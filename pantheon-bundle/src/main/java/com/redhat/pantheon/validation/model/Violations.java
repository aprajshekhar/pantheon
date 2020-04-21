package com.redhat.pantheon.validation.model;

import java.util.HashMap;
import java.util.Map;

public class Violations {
    private Map<String, ErrorDetails> errorMap = new HashMap<>();


    public Violations add(String violation, ErrorDetails detail){
        errorMap.put(violation, detail);
        return this;
    }

    public ErrorDetails get(String violation){
        return errorMap.get(violation);
    }

    public boolean hasViolations(){
        return !errorMap.isEmpty();
    }
}
