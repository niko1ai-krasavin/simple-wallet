package com.example.simplewallet.exceptions;

import java.math.BigDecimal;

public class LowBalanceWalletException extends RuntimeException {

    public LowBalanceWalletException(BigDecimal sender, BigDecimal amount) {
        super("A sender wallet with low balance. The balance is "
                + sender + ". The amount of money transfer is " + amount + ".");
    }
}