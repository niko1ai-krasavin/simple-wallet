package com.example.simplewallet.controllers;

import com.example.simplewallet.SimpleWalletApplication;
import com.example.simplewallet.dto.TransferMoneyDTO;
import com.example.simplewallet.dto.WalletDTO;
import com.example.simplewallet.entities.Transaction;
import com.example.simplewallet.entities.Wallet;
import com.example.simplewallet.services.TransferManager;
import com.example.simplewallet.validators.WalletApiValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

@RestController
public class WalletController {

    @Autowired
    private TransferManager transferManager;

    @Autowired
    private WalletApiValidator walletApiValidator;

    private final ReentrantLock controllerLock = new ReentrantLock(true);

    @GetMapping("/wallets")
    public Collection<Wallet> all() {
        controllerLock.lock();
        try {
            return SimpleWalletApplication.WALLETS.values();
        } finally {
            controllerLock.unlock();
        }
    }

    @PostMapping("/wallets")
    public Wallet newWallet(@RequestBody WalletDTO walletDTO) {
        if (walletApiValidator.doWalletValidation(walletDTO)) {
            controllerLock.lock();
            try {
                Wallet wallet = new Wallet();
                wallet.setId((long) SimpleWalletApplication.WALLETS.size() + 1);
                wallet.setBalance(new BigDecimal(walletDTO.getBalance()).setScale(2, RoundingMode.HALF_UP));
                SimpleWalletApplication.WALLETS.put(wallet.getId(), wallet);
                return SimpleWalletApplication.WALLETS.get(wallet.getId());
            } finally {
                controllerLock.unlock();
            }
        }
        return null;
    }

    @GetMapping("/wallets/{id}")
    public Wallet one(@PathVariable long id) {
        if (walletApiValidator.isWalletExist(id)) {
            controllerLock.lock();
            try {
                return SimpleWalletApplication.WALLETS.get(id);
            } finally {
                controllerLock.unlock();
            }
        }
        return null;
    }

    @DeleteMapping("/wallets/{id}/delete")
    public void deleteWallet(@PathVariable long id) {
        if (walletApiValidator.isWalletExist(id)) {
            controllerLock.lock();
            try {
                SimpleWalletApplication.WALLETS.remove(id);
            } finally {
                controllerLock.unlock();
            }
        }
    }

    @PostMapping("wallets/{id}/transfer")
    public Transaction transferMoney(@RequestBody TransferMoneyDTO transferMoneyDTO, @PathVariable Long id) {
        if (
                walletApiValidator.isWalletExist(id) &&
                        walletApiValidator.doMoneyTransferValidation(transferMoneyDTO, id)
        ) {
            controllerLock.lock();
            try {
                return transferManager.doMoneyTransfer(id, transferMoneyDTO);
            } finally {
                controllerLock.unlock();
            }
        }
        return null;
    }
}