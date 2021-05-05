package com.example.simplewallet.exceptions;


public class BalanceLessThanZeroException extends RuntimeException {
    public BalanceLessThanZeroException() {
        super("The wallet balance is less than zero.");
    }
}
