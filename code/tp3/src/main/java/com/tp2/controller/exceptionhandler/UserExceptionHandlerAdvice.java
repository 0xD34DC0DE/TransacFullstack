package com.tp2.controller.exceptionhandler;

import com.tp2.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandlerAdvice {

    private ResponseEntity<String> createResponseEntity(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus).body(message);
    }

    @ExceptionHandler(NullUserException.class)
    public ResponseEntity handleNullUserException(NullUserException e) {
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Server error");
    }

    @ExceptionHandler(DuplicateNASException.class)
    public ResponseEntity handleDuplicateNASException(DuplicateNASException e) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(DuplicateLoginException.class)
    public ResponseEntity handleDuplicateLoginException(DuplicateLoginException e) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MinisterInvalidNASException.class)
    public ResponseEntity handleMinisterInvalidNASException(MinisterInvalidNASException e) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(NullCredentialException.class)
    public ResponseEntity handleMinisterInvalidNASException(NullCredentialException e) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleBadCredentialException(BadCredentialsException e) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(NonexistentUserException.class)
    public ResponseEntity handleNonexistentUserException(NonexistentUserException e) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UnhandledException.class)
    public ResponseEntity handleUnexpectedException(UnhandledException e) {
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }



}
