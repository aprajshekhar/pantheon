package com.redhat.pantheon.validation;

import com.redhat.pantheon.extension.Event;
import com.redhat.pantheon.extension.EventProcessingExtension;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import java.util.List;

@Component(
        service = EventProcessingExtension.class
)
public class ValidionEventProcessor implements EventProcessingExtension {

    private ValidationsCompleteNotifierService validationsCompleteNotifierService;

    @Activate
    public ValidionEventProcessor(
            @Reference ValidationsCompleteNotifierService validationsCompleteNotifierService){
        this.validationsCompleteNotifierService = validationsCompleteNotifierService;
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
