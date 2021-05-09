package com.example.simplewallet.exceptions;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(long id) {
        super("The wallet with the specified ID = " + id + " was not found.");
    }
}