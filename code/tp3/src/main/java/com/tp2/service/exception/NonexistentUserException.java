package com.tp2.service.exception;

public class NonexistentUserException extends Exception {
    public NonexistentUserException() {
        super("User not found");
    }
}
