package com.example.simplewallet.validators;


import com.example.simplewallet.SimpleWalletApplication;
import com.example.simplewallet.dto.TransferMoneyDTO;
import com.example.simplewallet.dto.WalletDTO;
import com.example.simplewallet.entities.Wallet;
import com.example.simplewallet.exceptions.*;

import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@NoArgsConstructor
public class WalletApiValidator {

    public boolean doMoneyTransferValidation(TransferMoneyDTO transferMoneyDTO, Long senderId) {

        if(transferMoneyDTO == null) {
            throw new RequestBodyIsIncorrectException();
        }

        try {
            Wallet walletSender = SimpleWalletApplication.WALLETS.get(senderId);
            Wallet walletReceiver = SimpleWalletApplication.WALLETS.get(transferMoneyDTO.getReceiverId());
            BigDecimal balanceSender = walletSender.getBalance();
            BigDecimal amount = new BigDecimal(transferMoneyDTO.getAmount()).setScale(2, RoundingMode.HALF_UP);

            if (walletReceiver == null) {
                throw new WalletNotFoundException(transferMoneyDTO.getReceiverId());
            }

            if (BigDecimal.ZERO.compareTo(amount) >= 0) {
                throw new AmountLessThanOrEqualZeroException();
            }

            if (isSenderAndReceiverTheSameWallets(senderId, transferMoneyDTO.getReceiverId())) {
                throw new SenderAndReceiverTheSameException();
            }

            if (isNotEnoughMoneyOnWalletSender(balanceSender, amount)) {
                throw new LowBalanceWalletException(balanceSender, amount);
            }
        } catch (NullPointerException ex) {
            throw new RequestBodyIsIncorrectException();
        }
        return true;
    }

    public boolean doWalletValidation(WalletDTO walletDTO) {

        if(walletDTO == null) {
            throw new RequestBodyIsIncorrectException();
        }

        try {
            if (isBalanceOfWalletLessThanZero(walletDTO.getBalance())) {
                throw new BalanceLessThanZeroException();
            }
        } catch (NullPointerException ex) {
            throw new RequestBodyIsIncorrectException();
        }
        return true;
    }

    public boolean isWalletExist(long id) {
        if (SimpleWalletApplication.WALLETS.get(id) == null) {
            throw new WalletNotFoundException(id);
        }
        return true;
    }

    private boolean isNotEnoughMoneyOnWalletSender(BigDecimal balanceSender, BigDecimal amount) {
        return balanceSender.compareTo(amount) < 0;
    }

    private boolean isSenderAndReceiverTheSameWallets(Long senderId, Long receiverId) {
        return (long) senderId == (long) receiverId;
    }

    private boolean isBalanceOfWalletLessThanZero(double balance) {
        return balance < 0;
    }
}
