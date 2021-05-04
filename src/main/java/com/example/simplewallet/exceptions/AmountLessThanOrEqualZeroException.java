package com.example.simplewallet.exceptions;


public class AmountLessThanOrEqualZeroException extends RuntimeException {
    public AmountLessThanOrEqualZeroException() {
        super("Amount less than or equal 0");
    }
}
