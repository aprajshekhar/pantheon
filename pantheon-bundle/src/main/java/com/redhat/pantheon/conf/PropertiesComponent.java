package com.redhat.pantheon.conf;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.ComponentContext;

import java.util.Dictionary;
import java.util.Enumeration;

@Component(name = "properties-component", immediate = true, configurationPid = "com.redhat.pantheon.static.properties")
public class PropertiesComponent {
    @Activate
    public void activate(ComponentContext context) {
        Dictionary<String, Object> properties = context.getProperties();
        Enumeration<String> keys = properties.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            System.out.println("logging from admin:"+key + " = " + properties.get(key));
        }
    }
}
