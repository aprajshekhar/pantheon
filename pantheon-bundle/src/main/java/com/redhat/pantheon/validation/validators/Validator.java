package com.redhat.pantheon.validation.validators;

import com.redhat.pantheon.validation.model.Violations;

/**
 * The interface which all validators should implement
 * @author A.P. Rajshekhar
 */
public interface Validator {
    /**
     * Validates an object(s) by executing the validation logic
     * The implementing class provides the logic and returns the errors
     * if any, as an instance of Violations
     *
     * @return the violations the validation errors
     */
    Violations validate();

    /**
     * Gets name of the validator
     *
     * @return the name of the validator
     */
    String getName();
}
