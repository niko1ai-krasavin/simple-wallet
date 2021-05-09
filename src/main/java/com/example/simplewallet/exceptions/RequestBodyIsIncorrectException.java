package com.example.simplewallet.exceptions;

public class RequestBodyIsIncorrectException extends RuntimeException {
    public RequestBodyIsIncorrectException() {
        super("The request body is incorrect.");
    }
}