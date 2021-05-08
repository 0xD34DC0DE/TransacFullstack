package com.tp2.service.exception;

public class NullCredentialException extends Exception {
    public NullCredentialException() {
        super("Missing credentials");
    }
}
