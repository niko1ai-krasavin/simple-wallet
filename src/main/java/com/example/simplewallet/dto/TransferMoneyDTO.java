package com.example.simplewallet.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransferMoneyDTO {
    private Long id;
    private Long receiverId;
    private Double amount;
}