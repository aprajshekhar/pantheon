package com.redhat.pantheon.validation.events;

import com.redhat.pantheon.validation.model.CombinedViolations;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import java.util.ArrayList;
import java.util.List;

/**
 * The service that keeps track of all the listeners for validations completed event
 *
 * @author A.P. Rajshekhar
 */
@Component(service = ValidationsCompleteNotifierService.class,
scope = ServiceScope.SINGLETON)
public class ValidationsCompleteNotifierService {
    private List<ValidationsCompleteListener> listeners;

    /**
     * Initialize the list that holds all the registered listeners
     */
    @Activate
    public void initialize(){
        listeners = new ArrayList<>();
    }

    /**
     * Register validations complete listener.
     *
     * @param validationsCompleteListener the validations complete listener
     */
    public void registerValidationsCompleteListener(ValidationsCompleteListener validationsCompleteListener){
        this.listeners.add(validationsCompleteListener);
    }


    /**
     * Unregister validations complete listener.
     *
     * @param validationsCompleteListener the validations complete listener
     */
    public void unregisterValidationsCompleteListener(ValidationsCompleteListener validationsCompleteListener){
        this.listeners.remove(validationsCompleteListener);
    }

    /**
     * Notify validations complete listeners.
     *
     * @param combinedViolations the combined violations
     */
    public void notifyValidationsCompleteListeners(CombinedViolations combinedViolations){
        this.listeners.forEach(validationsCompleteListener -> validationsCompleteListener.onValidationsComplete(combinedViolations));
    }
}
