package com.tp2.service.exception;

public class NullEmailException extends Exception {
    public NullEmailException() {
        super("Email is null");
    }
}
