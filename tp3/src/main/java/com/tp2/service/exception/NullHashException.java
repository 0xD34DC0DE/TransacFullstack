package com.tp2.service.exception;

public class NullHashException extends Exception {
    public NullHashException() {
        super("Hash is null");
    }
}
