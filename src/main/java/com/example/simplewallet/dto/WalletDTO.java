package com.example.simplewallet.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WalletDTO {
    private Long id;
    private Double balance;
}