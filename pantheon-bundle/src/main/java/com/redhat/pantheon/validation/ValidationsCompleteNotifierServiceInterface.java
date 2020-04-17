package com.redhat.pantheon.validation;

public interface ValidationsCompleteNotifierServiceInterface {
    void registerValidationsCompleteListener(ValidationsCompleteListener validationsCompleteListener);

    void unregisterValidationsCompleteListener(ValidationsCompleteListener validationsCompleteListener);

    void notifyValidationsCompleteListeners(CombinedViolations combinedViolations);
}
