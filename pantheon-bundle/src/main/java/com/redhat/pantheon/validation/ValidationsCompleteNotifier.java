package com.redhat.pantheon.validation;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;

@Component(service = ValidationsCompleteNotifier.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Provides validation notification related services",
                Constants.SERVICE_VENDOR + "=Red Hat Content Tooling team"
        })
public class ValidationsCompleteNotifier {
    private List<ValidationsCompleteListener> listeners = new ArrayList<>();

    public void registerValidationsCompleteListener(ValidationsCompleteListener validationsCompleteListener){
        this.listeners.add(validationsCompleteListener);
    }

    public void unregisterValidationsCompleteListener(ValidationsCompleteListener validationsCompleteListener){
        this.listeners.remove(validationsCompleteListener);
    }

    public void notifyValidationsCompleteListeners( CombinedViolations combinedViolations){
        this.listeners.forEach(validationsCompleteListener -> validationsCompleteListener.onValidationsComplete(combinedViolations));
    }
}
