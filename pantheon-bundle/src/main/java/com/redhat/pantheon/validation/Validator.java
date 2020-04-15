package com.redhat.pantheon.validation;

public interface Validator {
    Violations validate();
    String getName();
}
