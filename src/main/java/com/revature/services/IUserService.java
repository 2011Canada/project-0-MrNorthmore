package com.revature.services;

import com.revature.models.Employee;
import com.revature.models.User;

public interface IUserService {
	
	public User getCurrentUser();
	
	public void setCurrentUser(User user);
	
	public Employee getEm();
	
	public void setEm(Employee em);
	
	public User login(String username, String password);
	
	public Employee loginAsEmployee(String username, String password);
	
	public void createUserAccount(String username, String password);
	
	public boolean isValidUsername(String username);
	
	public void logout();
}
