package com.reactifyx.exception;

public class IoCCircularDepException extends Exception {
    public IoCCircularDepException(String message) {
        super(message);
    }
}
