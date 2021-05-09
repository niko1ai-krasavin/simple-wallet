package com.example.simplewallet.services;

import com.example.simplewallet.SimpleWalletApplication;
import com.example.simplewallet.dto.TransferMoneyDTO;
import com.example.simplewallet.entities.Transaction;
import com.example.simplewallet.entities.Wallet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Getter
@NoArgsConstructor
public class TransferManager {

    private long senderId;
    private TransferMoneyDTO request;
    private Transaction transaction;

    private final ReentrantLock transactionRepoLock = new ReentrantLock(true);

    public Transaction doMoneyTransfer(long senderId, TransferMoneyDTO request) {
        this.senderId = senderId;
        this.request = request;
        doTransfer();
        return getTransaction();
    }

    private void doTransfer() {

        Wallet walletSender = SimpleWalletApplication.WALLETS.get(senderId);
        Wallet walletReceiver = SimpleWalletApplication.WALLETS.get(request.getReceiverId());

        BigDecimal balanceReceiver = walletReceiver.getBalance();
        BigDecimal balanceSender = walletSender.getBalance();
        BigDecimal amount = new BigDecimal(request.getAmount()).setScale(2, RoundingMode.HALF_UP);

        BigDecimal newBalanceOfReceiver = balanceReceiver.add(amount).setScale(2, RoundingMode.HALF_UP);
        BigDecimal newBalanceOfSender = balanceSender.subtract(amount).setScale(2, RoundingMode.HALF_UP);

        walletReceiver.setBalance(newBalanceOfReceiver);
        walletSender.setBalance(newBalanceOfSender);

        transactionRepoLock.lock();
        try {
            transaction = new Transaction(
                    (long) (SimpleWalletApplication.TRANSACTIONS.size() + 1),
                    request.getReceiverId(),
                    senderId,
                    amount
            );
            SimpleWalletApplication.TRANSACTIONS.put(transaction.getId(), transaction);
        } finally {
            transactionRepoLock.unlock();
        }
    }
}