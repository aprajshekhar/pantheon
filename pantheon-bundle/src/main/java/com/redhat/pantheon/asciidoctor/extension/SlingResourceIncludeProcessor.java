package com.redhat.pantheon.asciidoctor.extension;

import com.redhat.pantheon.conf.GlobalConfig;
import com.redhat.pantheon.helper.Symlinks;
import com.redhat.pantheon.model.api.FileResource;
import com.redhat.pantheon.model.api.SlingModel;
import com.redhat.pantheon.model.assembly.TableOfContents;
import com.redhat.pantheon.model.module.Module;
import com.redhat.pantheon.model.module.ModuleLocale;
import com.redhat.pantheon.model.document.SourceContent;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.asciidoctor.ast.Document;
import org.asciidoctor.extension.IncludeProcessor;
import org.asciidoctor.extension.PreprocessorReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.redhat.pantheon.model.api.util.ResourceTraversal.traverseFrom;
import static org.apache.jackrabbit.JcrConstants.JCR_PRIMARYTYPE;

public class SlingResourceIncludeProcessor extends IncludeProcessor {

    private final Logger log = LoggerFactory.getLogger(SlingResourceIncludeProcessor.class);

    private ResourceResolver resolver;
    private Resource parent;
    private TableOfContents toc = new TableOfContents();

    public SlingResourceIncludeProcessor(final Resource resource) {
        this.resolver = resource.getResourceResolver();
        this.parent = resource.getParent();
    }

    @Override
    public boolean handles(String target) {
        return true;
    }

    @Override
    public void process(Document document, PreprocessorReader reader, String target, Map<String, Object> attributes) {
        log.trace("Attempting to include {}", target);
        log.trace("Parent: {}", parent);
        log.trace("reader.getDir(): {}", reader.getDir());
        log.trace("reader.getFile(): {}", reader.getFile());

        String fixedTarget = target;
        if (reader.getFile().isEmpty()) {
            log.trace("At top level, no need to fix target");
        } else {
            fixedTarget = reader.getDir() + "/" + target;
            log.trace("Fixed target: " + fixedTarget);
        }
        Resource includeResource = resolver.getResource(parent, fixedTarget);
        String content = "Invalid include: " + target;

        if (includeResource == null) {
            //Check if maybe there are symlinks?
            includeResource = resolveWithSymlinks(fixedTarget, parent);
        }

        if(includeResource != null) {
            SlingModel includedResourceAsModel = includeResource.adaptTo(SlingModel.class);

            // Included resource might be a plain file or another module
            if( includedResourceAsModel.field(JCR_PRIMARYTYPE, String.class).get().equals("pant:module") ) {
                Module module = includedResourceAsModel.adaptTo(Module.class);
                toc.addEntry((String) attributes.get("leveloffset"), module);
                // TODO, right now only default locale and latest (draft) version of the module are used
                content = traverseFrom(module)
                        .toChild(module1 -> module.locale(GlobalConfig.DEFAULT_MODULE_LOCALE))
                        .toChild(ModuleLocale::source)
                        .toChild(SourceContent::draft)
                        .toChild(FileResource::jcrContent)
                        .toField(FileResource.JcrContent::jcrData)
                        .get();
                content = new StringBuilder(":pantheon_module_id: ")
                        .append(module.uuid().get())
                        .append("\r\n")
                        .append(content)
                        .append("\r\n")
                        .append(":!pantheon_module_id:")
                        .append("\r\n")
                        .toString();
            } else {
                // It's a plain file
                FileResource file = includedResourceAsModel.adaptTo(FileResource.class);
                content = file.jcrContent().get()
                        .jcrData().get();
            }
        } else {
            log.warn("Could not find include for {}", target);
        }

        reader.push_include(content, target, target, 1, attributes);
    }

    private Resource resolveWithSymlinks(String path, Resource pathParent) {

        Resource resource = Symlinks.resolve(resolver, pathParent.getPath() + "/" + path);
        if ("sling:nonexisting".equals(resource.getResourceType())) {
            return null;
        }
        return resource;
    }

    public TableOfContents getTableOfContents() {
        return toc;
    }
}
