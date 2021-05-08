package com.tp2.service.exception;

public class NoPermisFoundException extends Exception {
    public NoPermisFoundException() {
        super("No permit found");
    }
}
