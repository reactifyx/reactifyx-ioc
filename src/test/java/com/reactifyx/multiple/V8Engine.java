package com.reactifyx.multiple;

import com.reactifyx.Autowired;
import com.reactifyx.Component;

@Component
public class V8Engine implements Engine {
    private final CircularDependency circularDependency;

    @Autowired
    public V8Engine(CircularDependency circularDependency) {
        this.circularDependency = circularDependency;
    }

    public String getName() {
        return "V8 and " + circularDependency.getName();
    }
}
