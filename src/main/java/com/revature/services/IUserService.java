package com.revature.services;

import com.revature.models.User;

public interface IUserService {
	
	public User login(String username, String password);
	public void logout();
	public void createUserAccount(String username, String password);
}
