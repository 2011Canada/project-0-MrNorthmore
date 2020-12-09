package com.revature.services;

public interface IUserService {
	
	public boolean login(String username, String password);
	public void logout();

}
