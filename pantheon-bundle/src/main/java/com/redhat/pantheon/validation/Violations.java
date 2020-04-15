package com.redhat.pantheon.validation;

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
}
