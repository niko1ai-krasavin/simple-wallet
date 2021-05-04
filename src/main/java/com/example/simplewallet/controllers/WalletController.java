package com.example.simplewallet.controllers;

import com.example.simplewallet.SimpleWalletApplication;
import com.example.simplewallet.dto.TransactionDTO;
import com.example.simplewallet.dto.WalletDTO;
import com.example.simplewallet.etities.Transaction;
import com.example.simplewallet.etities.Wallet;
import com.example.simplewallet.services.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
public class WalletController {

    @Autowired
    private TransactionManager transactionManager;

    @GetMapping("/wallets")
    public List<Wallet> all() {
        return SimpleWalletApplication.WALLETS;
    }

    @PostMapping("/wallets")
    public Wallet newWallet(@RequestBody WalletDTO walletDTO) {
        Wallet wallet = new Wallet();
        wallet.setId((long) SimpleWalletApplication.WALLETS.size()+1);
        wallet.setBalance(new BigDecimal(walletDTO.getBalance()).setScale(2, RoundingMode.HALF_UP));
        SimpleWalletApplication.WALLETS.add(wallet);
        return SimpleWalletApplication.WALLETS.get(SimpleWalletApplication.WALLETS.size()-1);
    }

    @GetMapping("/wallets/{id}")
    public Wallet one(@PathVariable long id) {
        return SimpleWalletApplication.WALLETS.get((int) (id - 1));
    }

    @PutMapping("/wallets/{id}")
    public Wallet replaceWallet(@RequestBody WalletDTO walletDTO, @PathVariable long id) {
        Wallet wallet = new Wallet();
        wallet.setId(walletDTO.getId());
        wallet.setBalance(new BigDecimal(walletDTO.getBalance()).setScale(2, RoundingMode.HALF_UP));
        SimpleWalletApplication.WALLETS.set((int) (id-1), wallet);
        return SimpleWalletApplication.WALLETS.get((int) (id-1));
    }

    @DeleteMapping("/wallets/{id}")
    public void deleteWallet(@PathVariable long id) {
        SimpleWalletApplication.WALLETS.remove(id - 1);
    }

    @PostMapping("wallets/{id}/transfer")
    public Transaction transferMoney(@RequestBody TransactionDTO transactionDTO, @PathVariable Long id) {
        return transactionManager.doTransaction(id, transactionDTO);
    }
}
