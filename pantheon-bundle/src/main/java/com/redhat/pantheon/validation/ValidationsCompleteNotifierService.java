package com.redhat.pantheon.validation;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import java.util.ArrayList;
import java.util.List;

@Component(service = ValidationsCompleteNotifierService.class,
scope = ServiceScope.SINGLETON)
public class ValidationsCompleteNotifierService {
    private List<ValidationsCompleteListener> listeners;

    @Activate
    public void initialize(){
        listeners = new ArrayList<>();
    }

    public void registerValidationsCompleteListener(ValidationsCompleteListener validationsCompleteListener){
        this.listeners.add(validationsCompleteListener);
    }


    public void unregisterValidationsCompleteListener(ValidationsCompleteListener validationsCompleteListener){
        this.listeners.remove(validationsCompleteListener);
    }

    public void notifyValidationsCompleteListeners(CombinedViolations combinedViolations){
        this.listeners.forEach(validationsCompleteListener -> validationsCompleteListener.onValidationsComplete(combinedViolations));
    }
}
