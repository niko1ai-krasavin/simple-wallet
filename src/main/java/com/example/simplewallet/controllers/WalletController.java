package com.example.simplewallet.controllers;

import com.example.simplewallet.SimpleWalletApplication;
import com.example.simplewallet.dto.TransactionDTO;
import com.example.simplewallet.dto.WalletDTO;
import com.example.simplewallet.etities.Transaction;
import com.example.simplewallet.etities.Wallet;
import com.example.simplewallet.services.TransactionManager;
import com.example.simplewallet.validators.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

@RestController
public class WalletController {

    @Autowired
    private TransactionManager transactionManager;

    @Autowired
    private TransactionValidator transactionValidator;

    @GetMapping("/wallets")
    public Collection<Wallet> all() {
        return SimpleWalletApplication.WALLETS.values();
    }

    @PostMapping("/wallets")
    public Wallet newWallet(@RequestBody WalletDTO walletDTO) {
        Wallet wallet = new Wallet();
        wallet.setId((long) SimpleWalletApplication.WALLETS.size()+1);
        wallet.setBalance(new BigDecimal(walletDTO.getBalance()).setScale(2, RoundingMode.HALF_UP));
        SimpleWalletApplication.WALLETS.put(wallet.getId(), wallet);
        return SimpleWalletApplication.WALLETS.get(wallet.getId());
    }

    @GetMapping("/wallets/{id}")
    public Wallet one(@PathVariable long id) {
        return SimpleWalletApplication.WALLETS.get(id);
    }

    /**=========================================
    @PutMapping("/wallets/{id}")
    public Wallet replaceWallet(@RequestBody WalletDTO walletDTO, @PathVariable long id) {
        Wallet wallet = new Wallet();
        wallet.setId(walletDTO.getId());
        wallet.setBalance(new BigDecimal(walletDTO.getBalance()).setScale(2, RoundingMode.HALF_UP));
        return SimpleWalletApplication.WALLETS.replace(id, wallet);
    }
     **/

    @DeleteMapping("/wallets/{id}")
    public void deleteWallet(@PathVariable long id) {
        SimpleWalletApplication.WALLETS.remove(id);
    }

    @PostMapping("wallets/{id}/transfer")
    public Transaction transferMoney(@RequestBody TransactionDTO transactionDTO, @PathVariable Long id) {
        if(transactionValidator.doValidation(transactionDTO, id)) {
            return transactionManager.doTransaction(id, transactionDTO);
        }
        return null;
    }
}
