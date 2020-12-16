package com.revature.services;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;

public interface IBankService {
	public void applyForAccount(User User, Double initialBalance);
	
	public void viewAccountBalance();
	
	public void depositOrWithdraw(Account account, Double amount, String operation);
	
	public List<Account> getAllUsersAccounts(User user);
}
