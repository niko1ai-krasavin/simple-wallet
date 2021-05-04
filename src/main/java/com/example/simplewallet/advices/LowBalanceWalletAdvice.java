package com.example.simplewallet.advices;

import com.example.simplewallet.exceptions.LowBalanceWalletException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LowBalanceWalletAdvice {

    @ResponseBody
    @ExceptionHandler(LowBalanceWalletException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String lowBalanceWalletHandler(LowBalanceWalletException ex) {
        return ex.getMessage();
    }
}
