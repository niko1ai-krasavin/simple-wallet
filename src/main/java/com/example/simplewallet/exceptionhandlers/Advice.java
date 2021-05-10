package com.example.simplewallet.exceptionhandlers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advice {
    private String timestamp;
    private int status;
    private String error;
    private String message;
}