package com.reactifyx.childparent;

import com.reactifyx.Autowired;
import com.reactifyx.Component;

@Component
public class Parent {
    protected Dependency dependency;

    @Autowired
    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }
}
