package com.redhat.pantheon.validation.validators;

import com.redhat.pantheon.validation.model.ErrorDetails;
import com.redhat.pantheon.validation.model.Violations;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class NotNullValidator implements Validator {
    private List<Object> objectsToValidate;

    @Activate
    public NotNullValidator() {
        setObjectsToValidate(new ArrayList<>());
    }

    public NotNullValidator(List<Object> objectsToValidate) {
        this.setObjectsToValidate(objectsToValidate);
    }

    @Override
    public Violations validate() {
        return checkIfNull(new Violations());
    }

    private Violations checkIfNull(Violations violations) {
        if(!isNull()){
            return violations;
        }
        return violations.add("Not null validation failed",
                new ErrorDetails().add("One of the objects in the list has null value"));
    }

    private boolean isNull() {
        return getObjectsToValidate().stream().anyMatch(Objects::isNull);
    }

    /**
     * Gets name of the validator
     *
     * @return the name of the validator
     */
    @Override
    public String getName() {
        return null;
    }

    public List<Object> getObjectsToValidate() {
        return objectsToValidate;
    }

    public void setObjectsToValidate(List<Object> objectsToValidate) {
        this.objectsToValidate = objectsToValidate;
    }
}
