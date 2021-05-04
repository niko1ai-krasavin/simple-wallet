package com.example.simplewallet.advices;

import com.example.simplewallet.exceptions.AmountLessThanOrEqualZeroException;
import com.example.simplewallet.exceptions.LowBalanceWalletException;
import com.example.simplewallet.exceptions.SenderAndReceiverTheSameException;
import com.example.simplewallet.exceptions.WalletNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseBody
    @ExceptionHandler(
            {
                    AmountLessThanOrEqualZeroException.class,
                    LowBalanceWalletException.class,
                    SenderAndReceiverTheSameException.class,
                    WalletNotFoundException.class
            })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Advice doExceptionHandling(RuntimeException ex) {
        return new Advice(ex.getMessage());
    }

    /**
    @ResponseBody
    @ExceptionHandler(SenderAndReceiverTheSameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    SenderAndReceiverTheSameException senderAndReceiverTheSameExceptionHandler(SenderAndReceiverTheSameException ex) {
        return ex;
    }

    @ResponseBody
    @ExceptionHandler(AmountLessThanOrEqualZeroException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    AmountLessThanOrEqualZeroException amountLessThanOrEqualZeroException(AmountLessThanOrEqualZeroException ex) {
        return ex;
    }
    **/

}
