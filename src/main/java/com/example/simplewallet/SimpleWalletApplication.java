package com.example.simplewallet;

import com.example.simplewallet.etities.Transaction;
import com.example.simplewallet.etities.Wallet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class SimpleWalletApplication {

	//Repository is hardcoded
	public static final Map<Long, Wallet> WALLETS = new HashMap<>();
	public static final Map<Long, Transaction> TRANSACTIONS = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(SimpleWalletApplication.class, args);

		//Creating Wallet Object to store in repository (hardcoded)
		Wallet wallet1 = new Wallet();
		wallet1.setId(1L);
		wallet1.setBalance(new BigDecimal(0.00).setScale(2));
		WALLETS.put(1L, wallet1);
	}

}
