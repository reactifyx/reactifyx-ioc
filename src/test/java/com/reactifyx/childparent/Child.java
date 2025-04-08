package com.reactifyx.childparent;

import com.reactifyx.Component;

@Component
public class Child extends Parent {
    public String test() {
        return dependency.getName();
    }
}
