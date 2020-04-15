package com.redhat.pantheon.validation;

import java.util.ArrayList;
import java.util.List;

public class ErrorDetails {
    private List<String> details = new ArrayList<>();

    public ErrorDetails add(String detail){
        details.add(detail);
        return this;
    }

    public String getDetails(){
        return String.join("\n", details);
    }
}
