package com.tp2.service.exception;

public class NullUserException extends Exception {
    public NullUserException() {
        super("User is null");
    }
}
