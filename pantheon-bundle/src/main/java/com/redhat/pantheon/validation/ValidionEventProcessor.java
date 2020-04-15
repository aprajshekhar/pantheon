package com.redhat.pantheon.validation;

import com.redhat.pantheon.extension.Event;
import com.redhat.pantheon.extension.EventProcessingExtension;
import jdk.nashorn.internal.ir.annotations.Reference;

import java.util.List;

public class ValidionEventProcessor implements EventProcessingExtension {

    private ValidationsCompleteNotifier validationsCompleteNotifier;

    public ValidionEventProcessor(@Reference ValidationsCompleteNotifier validationsCompleteNotifier){
        this.validationsCompleteNotifier = validationsCompleteNotifier;
    }

    @Override
    public boolean canProcessEvent(Event event) {
        return ValidationTriggerEvent.class.isAssignableFrom(event.getClass());
    }

    @Override
    public void processEvent(Event event) throws Exception {
        ValidationTriggerEvent validationTriggerEvent = (ValidationTriggerEvent)event;
        processValidation(validationTriggerEvent.getValidators(), new CombinedViolations());
    }

    private void processValidation(List<Validator> validators, CombinedViolations combinedViolations) {
        validators.forEach(validator -> combinedViolations.add(validator.getName(), validator.validate()));
         this.validationsCompleteNotifier.notifyValidationsCompleteListeners(combinedViolations);
    }
}
