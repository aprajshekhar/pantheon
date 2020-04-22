package com.redhat.pantheon.validation.events;


import com.redhat.pantheon.validation.model.ValidationClientDetails;
import com.redhat.pantheon.validation.validators.Validator;
import org.osgi.service.component.annotations.Component;

import java.util.List;

@Component(service = SelectedValidationsService.class)
public class SelectedValidationsService {
    private List<Validator> validators;
    private ValidationClientDetails validationClientDetails;
    public SelectedValidationsService(){}
    public SelectedValidationsService(List<Validator> validators, ValidationClientDetails validationClientDetails) {
        this.validators = validators;
        this.setValidationClientDetails(validationClientDetails);
    }

    public List<Validator> getValidators() {
        return validators;
    }

    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }

    public ValidationClientDetails getValidationClientDetails() {
        return validationClientDetails;
    }

    public void setValidationClientDetails(ValidationClientDetails validationClientDetails) {
        this.validationClientDetails = validationClientDetails;
    }
}
