package com.tp2.service.exception;

public class DuplicateLoginException extends Exception {
    public DuplicateLoginException() {
        super("Login already exists");
    }
}
