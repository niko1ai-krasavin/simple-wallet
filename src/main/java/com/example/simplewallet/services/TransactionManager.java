package com.example.simplewallet.services;

import com.example.simplewallet.SimpleWalletApplication;
import com.example.simplewallet.dto.TransactionDTO;
import com.example.simplewallet.etities.Transaction;
import com.example.simplewallet.etities.Wallet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Getter
@NoArgsConstructor
public class TransactionManager {

    private long senderId;
    private TransactionDTO request;
    private Transaction transaction;

    public synchronized Transaction doTransaction(long senderId, TransactionDTO request) {
        this.senderId = senderId;
        this.request = request;
        doTransfer();
        return transaction;
    }

    private synchronized void doTransfer() {
        Wallet walletSender = SimpleWalletApplication.WALLETS.get(senderId);
        Wallet walletReceiver = SimpleWalletApplication.WALLETS.get(request.getReceiverId());

        BigDecimal balanceReceiver = walletReceiver.getBalance();
        BigDecimal balanceSender = walletSender.getBalance();
        BigDecimal amount = new BigDecimal(request.getAmount()).setScale(2, RoundingMode.HALF_UP);

        BigDecimal newBalanceOfReceiver = balanceReceiver.add(amount).setScale(2, RoundingMode.HALF_UP);
        BigDecimal newBalanceOfSender = balanceSender.subtract(amount).setScale(2, RoundingMode.HALF_UP);

        walletReceiver.setBalance(newBalanceOfReceiver);
        walletSender.setBalance(newBalanceOfSender);

        transaction = new Transaction(
                (long) (SimpleWalletApplication.TRANSACTIONS.size() + 1),
                request.getReceiverId(),
                senderId,
                amount
        );
    }
}
