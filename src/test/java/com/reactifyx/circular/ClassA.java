package com.reactifyx.circular;

import com.reactifyx.Autowired;
import com.reactifyx.Component;

@Component
public class ClassA {
    private final ClassB classB;

    @Autowired
    public ClassA(ClassB classB) {
        this.classB = classB;
    }

    public void methodA() {
        classB.methodB();
    }
}
