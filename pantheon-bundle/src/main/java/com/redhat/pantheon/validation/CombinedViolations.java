package com.redhat.pantheon.validation;

import java.util.*;

public class CombinedViolations {
    private Map<String, Violations> combined = new HashMap<>();

    public void add(String validatorName, Violations violations){
        this.combined.put(validatorName, violations);
    }

    public Violations getViolations(String validatorName){
        return this.combined.get(validatorName);
    }

    public Map<String, Violations>getAll(){
        return Collections.unmodifiableMap(this.combined);
    }


}
