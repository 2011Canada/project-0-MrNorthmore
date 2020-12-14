package com.revature.repositories;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;

// This is our banking data access object
public interface IBankDAO {
	
	// Here we define the behavior that our data access needs to implement
	
	public Account createOne(User user, Double initialBalance);
	
	public List<Account> getAllUserAccounts(User user);
	
	public User getOne();
	
	public Account updateBalance(Account account, Double amount);

}
