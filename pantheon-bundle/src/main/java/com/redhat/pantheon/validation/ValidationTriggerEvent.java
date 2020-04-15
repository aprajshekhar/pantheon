package com.redhat.pantheon.validation;

import com.redhat.pantheon.extension.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidationTriggerEvent implements Event {
    private List<Validator> validators = new ArrayList<>();

    public ValidationTriggerEvent(Validator... validators) {
        this.validators = new ArrayList<>(Arrays.asList(validators));
    }

    public List<Validator> getValidators() {
        return validators;
    }
}
