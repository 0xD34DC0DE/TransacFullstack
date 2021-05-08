package com.tp2.service.exception;

public class DuplicateNASException extends Exception {
    public DuplicateNASException() {
        super("NAS already exists");
    }
}
