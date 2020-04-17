package com.redhat.pantheon.validation;

import com.redhat.pantheon.extension.Event;
import com.redhat.pantheon.extension.EventProcessingExtension;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(
        service = EventProcessingExtension.class
)
public class ValidionEventProcessor implements EventProcessingExtension {

    private ValidationsCompleteNotifierService validationsCompleteNotifierService;

    @Activate
    public ValidionEventProcessor(
            @Reference ValidationsCompleteNotifierService validationsCompleteNotifierServiceInterface){
        this.validationsCompleteNotifierService = validationsCompleteNotifierServiceInterface;
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
         this.validationsCompleteNotifierService.notifyValidationsCompleteListeners(combinedViolations);
    }
}
