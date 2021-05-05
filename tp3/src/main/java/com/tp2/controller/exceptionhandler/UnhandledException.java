package com.tp2.controller.exceptionhandler;

public class UnhandledException extends Exception {
    public UnhandledException() {
        super("Server error");
    }
}
