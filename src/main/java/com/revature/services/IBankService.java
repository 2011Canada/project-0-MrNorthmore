package com.revature.services;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.Transfer;
import com.revature.models.User;

public interface IBankService {
	
	public void applyForAccount(User User, Double initialBalance);
	
	public void depositOrWithdraw(Account account, Double amount, String operation);
	
	public List<Account> getAllUsersAccounts(User user);
	
	public List<Account> getAllAccountsByUserId(int userId);
	
	public List<Transaction> getAllAccountTransactions(int accountNumber);
	
	public void approveAccount(int accountNumber);
	
	public void postTransfer(Account fromAccount, int toAccountNum, double amount);
	
	public List<Transfer> getTransfersToAccount(int accountNum);
	
	public boolean acceptTransfer(Account account, Transfer transfer);
}
