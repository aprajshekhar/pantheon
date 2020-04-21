package com.redhat.pantheon.validation.events;

import com.redhat.pantheon.extension.Event;
import com.redhat.pantheon.extension.EventProcessingExtension;

import com.redhat.pantheon.validation.validators.Validator;
import com.redhat.pantheon.validation.model.CombinedViolations;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * The event processor for  {@see ValidationTriggerEvent}.
 */
@Component(
        service = EventProcessingExtension.class
)
public class ValidionEventProcessor implements EventProcessingExtension {

    private ValidationsCompleteNotifierService validationsCompleteNotifierService;

    /**
     * Instantiates a new Validion event processor.
     *
     * @param validationsCompleteNotifierService the notifier service to be invoked
     *                                          when all the validators in the event payload are executed
     */
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

    /**
     *  Executes all the validators in the payload and combine all the results. Once all the
     *  validators are executed, the notifier service is invoked to notify all the listeners that
     *   all the validators have been executed
     * @param validators the validators to be executed
     * @param combinedViolations combined result
     */
    private void processValidation(List<Validator> validators, CombinedViolations combinedViolations) {
        validators.forEach(validator -> combinedViolations.add(validator.getName(), validator.validate()));
         this.validationsCompleteNotifierService.notifyValidationsCompleteListeners(combinedViolations);
    }
}
