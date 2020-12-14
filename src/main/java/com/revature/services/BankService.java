package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.repositories.BankDAO;

public class BankService implements IBankService {
	
	private BankDAO bd;
	private List<Account> accounts;
	
	public BankService(BankDAO bd) {
		this.bd = bd;
		this.accounts = null;
	}

	public void applyForAccount(User user, Double initialBalance) {
		this.bd.createOne(user, initialBalance);
	}

	public void viewAccountBalance() {
		return;
	}

	public void deposit(Account account, Double depositAmount) {
		Double newBalance = account.getBalance() + depositAmount;
		this.bd.updateBalance(account, newBalance);
	}

	public void withdraw(Account account, Double withdrawAmount) {
		Double newBalance = account.getBalance() - withdrawAmount;
		this.bd.updateBalance(account, newBalance);
		return;
		
	}

	public List<Account> getAllUsersAccounts(User user) {
		this.accounts = this.bd.getAllUserAccounts(user);
		return this.accounts;
	}
	
	public List<Account> getAllUsersAccounts() {
		return this.accounts;
	}
	
	public List<Account> getAllAccountsByUserId(int userId){
		List<Account> accountList = null;
		accountList = this.bd.getUsersAccountsById(userId);
		return accountList;
		
	}
	
	public List<Transaction> getAllAccountTransactions(int accountNumber) {
		List<Transaction> transactionList = null;
		transactionList = this.bd.findTransactionsByAccount(accountNumber);
		return transactionList;
	}
	
	

}
