package com.example.simplewallet;

import com.example.simplewallet.etities.Transaction;
import com.example.simplewallet.etities.Wallet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SimpleWalletApplication {

	//Repository is hardcoded
	public static final List<Wallet> WALLETS = new ArrayList<>();
	public static final List<Transaction> TRANSACTIONS = new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication.run(SimpleWalletApplication.class, args);

		//Code is hardcoded
		Wallet wallet1 = new Wallet();
		wallet1.setId(1L);
		wallet1.setBalance(new BigDecimal(0.00).setScale(2));

		WALLETS.add(wallet1);
	}

}
