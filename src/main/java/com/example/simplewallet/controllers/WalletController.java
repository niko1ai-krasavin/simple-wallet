package com.example.simplewallet.controllers;

import com.example.simplewallet.SimpleWalletApplication;
import com.example.simplewallet.dto.TransferMoneyDTO;
import com.example.simplewallet.dto.WalletDTO;
import com.example.simplewallet.etities.Transaction;
import com.example.simplewallet.etities.Wallet;
import com.example.simplewallet.services.TransferManager;
import com.example.simplewallet.validators.WalletApiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

@RestController
public class WalletController {

    @Autowired
    private TransferManager transferManager;

    @Autowired
    private WalletApiValidator walletApiValidator;

    @GetMapping("/wallets")
    public Collection<Wallet> all() {
        synchronized (SimpleWalletApplication.WALLETS) {
            return SimpleWalletApplication.WALLETS.values();
        }
    }

    @PostMapping("/wallets")
    public Wallet newWallet(@RequestBody WalletDTO walletDTO) {
        if(walletApiValidator.doWalletValidation(walletDTO)) {
            Wallet wallet = new Wallet();
            synchronized (SimpleWalletApplication.WALLETS) {
                wallet.setId((long) SimpleWalletApplication.WALLETS.size()+1);
                wallet.setBalance(new BigDecimal(walletDTO.getBalance()).setScale(2, RoundingMode.HALF_UP));
                SimpleWalletApplication.WALLETS.put(wallet.getId(), wallet);
                return SimpleWalletApplication.WALLETS.get(wallet.getId());
            }
        }
        return null;
    }

    @GetMapping("/wallets/{id}")
    public Wallet one(@PathVariable long id) {
        if(walletApiValidator.isWalletExist(id)) {
            synchronized (SimpleWalletApplication.WALLETS) {
                return SimpleWalletApplication.WALLETS.get(id);
            }
        }
        return null;
    }

    @DeleteMapping("/wallets/{id}/delete")
    public void deleteWallet(@PathVariable long id) {
        if(walletApiValidator.isWalletExist(id)) {
            synchronized (SimpleWalletApplication.WALLETS) {
                SimpleWalletApplication.WALLETS.remove(id);
            }
        }
    }

    @PostMapping("wallets/{id}/transfer")
    public Transaction transferMoney(@RequestBody TransferMoneyDTO transferMoneyDTO, @PathVariable Long id) {
        if(
                walletApiValidator.isWalletExist(id) &&
                walletApiValidator.doMoneyTransferValidation(transferMoneyDTO, id)
        ) {
            return transferManager.doMoneyTransfer(id, transferMoneyDTO);
        }
        return null;
    }
}
