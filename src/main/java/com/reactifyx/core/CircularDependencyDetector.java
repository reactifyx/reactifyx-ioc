package com.reactifyx.core;

import com.reactifyx.exception.IoCCircularDepException;

import java.util.HashSet;
import java.util.Set;

public class CircularDependencyDetector {
    private final Set<Class<?>> instantiationInProgress = new HashSet<>();

    public void startInstantiation(Class<?> clazz) throws IoCCircularDepException {
        if (instantiationInProgress.contains(clazz)) {
            throw new IoCCircularDepException("Circular dependency detected while instantiating " + clazz.getName());
        }
        instantiationInProgress.add(clazz);
    }

    public void finishInstantiation(Class<?> clazz) {
        instantiationInProgress.remove(clazz);
    }
}
