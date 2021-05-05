package com.tp2.service.exception;

public class HashingErrorException extends Exception {
    public HashingErrorException() {
        super("Error while hashing");
    }
}
