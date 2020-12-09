package com.revature.services;

public class UserService implements IUserService {
	
	private boolean userLoggedIn = false;

	public boolean isUserLoggedIn() {
		return userLoggedIn;
	}

	public void setUserLoggedIn(boolean userLoggedIn) {
		this.userLoggedIn = userLoggedIn;
	}

	public boolean login(String username, String password) {
		if(username.equals("matt") && password.equals("matt")) {
			return true;
		} else {
			return false;
		}
	}

	public void logout() {
		return;
	}
	
	
	
}
