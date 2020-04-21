package com.redhat.pantheon.validation.validators;

import com.redhat.pantheon.validation.model.ErrorDetails;
import com.redhat.pantheon.validation.model.Violations;
import java.util.List;
import java.util.Objects;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

@Component( service = NotNullValidator.class,
        property = {
            Constants.SERVICE_DESCRIPTION + "=Provides validation services",
            Constants.SERVICE_VENDOR + "=Red Hat Content Tooling team"
        }
)
public class NotNullValidator implements Validator {

    private List<Object> objectsToValidate;

//    @Activate
//    public void initialize() {
//        objectsToValidate = new ArrayList<>();
//    }
//
//    public NotNullValidator(Object... objectsToValidate) {
//        this.setObjectsToValidate(new ArrayList<>(
//                Arrays.asList(objectsToValidate)
//        ));
//    }
    @Override
    public Violations validate() {
        return checkIfNull(new Violations());
    }

    private Violations checkIfNull(Violations violations) {
        if (!isNull()) {
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
        return "NotNullValidator";
    }

    public List<Object> getObjectsToValidate() {
        return objectsToValidate;
    }

    public void setObjectsToValidate(List<Object> objectsToValidate) {
        this.objectsToValidate = objectsToValidate;
    }
}
