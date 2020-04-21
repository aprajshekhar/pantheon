package com.redhat.pantheon.validation.model;

import java.util.*;

/**
 * Holds all the violations reported by all the executed validators
 * @author A.P. Rajshekhar
 */
public class CombinedViolations {
    private Map<String, Violations> combined = new HashMap<>();

    /**
     * Add the violations reported by a validator
     *
     * @param validatorName the name of validator reporting the v
     * @param violations    the violations
     */
    public void add(String validatorName, Violations violations){
        this.combined.put(validatorName, violations);
    }

    /**
     * Get violations violations.
     *
     * @param validatorName the validator name
     * @return the violations
     */
    public Violations getViolations(String validatorName){
        return this.combined.get(validatorName);
    }

    /**
     * Get all map.
     *
     * @return the map
     */
    public Map<String, Violations>getAll(){
        return Collections.unmodifiableMap(this.combined);
    }


}
