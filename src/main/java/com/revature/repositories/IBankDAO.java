package com.revature.repositories;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.Transfer;
import com.revature.models.User;

public interface IBankDAO {
	
	public Account createOne(User user, Double initialBalance);
	
	public List<Account> getAllUserAccounts(User user);
	
	public Account getOne(int userId);
	
	public Account updateBalance(Account account, Double amount, String operation, Double transAmt);
	
	public List<Account> getUsersAccountsById(int userId);
	
	public List<Transaction> findTransactionsByAccount(int accountNumber);
	
	public void approveAccount(int accountNum);
	
	public List<Transfer> getIncomingTransfersByAccountNumber(int accountNum);
	
	public Transfer insertTransfer(Account fromAccount, int toAccountNum, Double amount);
	
	public boolean acceptTransfer(Account account, Transfer transfer);

}
