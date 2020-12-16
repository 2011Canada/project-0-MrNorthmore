package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.Transfer;
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

	public void depositOrWithdraw(Account account, Double amount, String operation) {
		if(operation.equals("deposit")) {
			Double newBalance = account.getBalance() + amount;
			this.bd.updateBalance(account, newBalance, operation, amount);
			return;
		} else if(operation.equals("withdraw")) {
			Double newBalance = account.getBalance() - amount;
			this.bd.updateBalance(account, newBalance, operation, amount);
			return;
		}
		
	}

	public List<Account> getAllUsersAccounts(User user) {
		this.accounts = this.bd.getAllUserAccounts(user);
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
	
	public void approveAccount(int accountNumber) {
		this.bd.approveAccount(accountNumber);
	}
	
	public void postTransfer(Account fromAccount, int toAccountNum, double amount) {
		this.bd.insertTransfer(fromAccount, toAccountNum, amount);
	}
	
	public List<Transfer> getTransfersToAccount(int accountNum) {
		List<Transfer> transfers = this.bd.getIncomingTransfersByAccountNumber(accountNum);
		return transfers;
		
	}
	
	public boolean acceptTransfer(Account account, Transfer transfer) {
		boolean isTransferSuccessful = this.bd.acceptTransfer(account, transfer);
		return isTransferSuccessful;
	}
	
	public boolean isValidAccountNumber(int accountNum) {
		Account account = this.bd.getOne(accountNum);
		if(account != null) {
			return true;
		} else {
			return false;
		}
	}
	

}
