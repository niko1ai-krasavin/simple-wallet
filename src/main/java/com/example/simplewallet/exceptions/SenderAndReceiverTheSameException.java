package com.example.simplewallet.exceptions;

public class SenderAndReceiverTheSameException extends RuntimeException {

    public SenderAndReceiverTheSameException() {
        super("You cannot transfer money because the sender and receiver are the same wallet.");
    }
}