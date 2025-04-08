package com.reactifyx.multiple;

import com.reactifyx.Autowired;
import com.reactifyx.Component;

@Component
public class CircularDependency {
    @Autowired
    private V8Engine v8Engine;

    public String getName() {
        return "CircularDependency" + "Of" + v8Engine.getClass().getSimpleName();
    }
}
