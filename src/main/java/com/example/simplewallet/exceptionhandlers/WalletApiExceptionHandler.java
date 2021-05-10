package com.example.simplewallet.exceptionhandlers;

import com.example.simplewallet.exceptions.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class WalletApiExceptionHandler {

    @ResponseBody
    @ExceptionHandler(
            {
                    AmountLessThanOrEqualZeroException.class,
                    LowBalanceWalletException.class,
                    SenderAndReceiverTheSameException.class,
                    BalanceLessThanZeroException.class,
                    RequestBodyIsIncorrectException.class
            })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Advice doExceptionHandling(RuntimeException ex) {
        Advice advice = new Advice();
        advice.setTimestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        advice.setStatus(400);
        advice.setError("Bad Request");
        advice.setMessage(ex.getMessage());
        return advice;
    }

    @ResponseBody
    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Advice doHandlingWalletNotFoundException(RuntimeException ex) {
        Advice advice = new Advice();
        advice.setTimestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        advice.setStatus(404);
        advice.setError("Not Found");
        advice.setMessage(ex.getMessage());
        return advice;
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Advice doHandlingHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Advice advice = new Advice();
        advice.setTimestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        advice.setStatus(400);
        advice.setError("Bad Request");
        advice.setMessage(ex.getMessage());
        return advice;
    }
}