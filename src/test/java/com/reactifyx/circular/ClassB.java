package com.reactifyx.circular;

import com.reactifyx.Autowired;
import com.reactifyx.Component;

@Component
public class ClassB {
    private final ClassA classA;

    @Autowired
    public ClassB(ClassA classA) {
        this.classA = classA;
    }

    public void methodB() {
        classA.methodA();
    }
}
