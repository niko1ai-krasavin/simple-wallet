package com.example.simplewallet.validators;


import com.example.simplewallet.SimpleWalletApplication;
import com.example.simplewallet.dto.TransactionDTO;
import com.example.simplewallet.etities.Wallet;
import com.example.simplewallet.exceptions.AmountLessThanOrEqualZeroException;
import com.example.simplewallet.exceptions.LowBalanceWalletException;
import com.example.simplewallet.exceptions.SenderAndReceiverTheSameException;
import com.example.simplewallet.exceptions.WalletNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@NoArgsConstructor
public class TransactionValidator {

    public synchronized boolean doValidation(TransactionDTO transactionDTO, Long senderId) {

        Wallet walletSender = SimpleWalletApplication.WALLETS.get(senderId);
        Wallet walletReceiver = null;
        BigDecimal balanceSender = walletSender.getBalance();
        BigDecimal amount = new BigDecimal(transactionDTO.getAmount()).setScale(2, RoundingMode.HALF_UP);

        try {
            walletReceiver = SimpleWalletApplication.WALLETS.get(transactionDTO.getReceiverId());
        } catch (IndexOutOfBoundsException ex) {
            throw new WalletNotFoundException(transactionDTO.getReceiverId());
        }

        if(BigDecimal.ZERO.compareTo(amount) >= 0) {
            throw new AmountLessThanOrEqualZeroException();
        }

        if(isSenderAndReceiverTheSameWallets(senderId, transactionDTO.getReceiverId())) {
            throw new SenderAndReceiverTheSameException();
        }

        if(isNotEnoughMoneyOnWalletSender(balanceSender, amount)) {
            throw new LowBalanceWalletException(balanceSender, amount);
        }

        return true;
    }

    private boolean isNotEnoughMoneyOnWalletSender(BigDecimal balanceSender, BigDecimal amount) {
        return balanceSender.compareTo(amount) < 0;
    }

    private boolean isSenderAndReceiverTheSameWallets(Long senderId, Long receiverId) {
        return (long) senderId == (long) receiverId;
    }
}
