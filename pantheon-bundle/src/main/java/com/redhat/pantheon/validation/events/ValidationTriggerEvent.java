package com.redhat.pantheon.validation.events;

import com.redhat.pantheon.extension.Event;
import com.redhat.pantheon.validation.validators.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This event triggers the validators to be executed
 *
 * @author A.P. Rajshekhar
 */
public class ValidationTriggerEvent implements Event {
    private List<Validator> validators = new ArrayList<>();

    /**
     * Instantiates a new Validation trigger event with validators as payload
     *
     * @param validators the validators that need to be executed
     */
    public ValidationTriggerEvent(Validator... validators) {
        this.validators = new ArrayList<>(Arrays.asList(validators));
    }

    /**
     * Gets validators that has been added as the payload of the event
     *
     * @return the validators in the payload
     */
    public List<Validator> getValidators() {
        return validators;
    }
}
