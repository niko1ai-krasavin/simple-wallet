package com.example.simplewallet.exceptionhandlers;

import com.example.simplewallet.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WalletApiExceptionHandler {

    @ResponseBody
    @ExceptionHandler(
            {
                    AmountLessThanOrEqualZeroException.class,
                    LowBalanceWalletException.class,
                    SenderAndReceiverTheSameException.class,
                    BalanceLessThanZeroException.class
            })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Advice doExceptionHandling(RuntimeException ex) {
        return new Advice(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Advice doExceptionHandlingNotFound(RuntimeException ex) {
        return new Advice(ex.getMessage());
    }
}
