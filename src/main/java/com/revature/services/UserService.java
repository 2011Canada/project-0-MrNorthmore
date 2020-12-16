package com.revature.services;

import com.revature.exceptions.EmployeeNotFoundException;
import com.revature.exceptions.InternalErrorException;
import com.revature.exceptions.UnableToCreateUserException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.repositories.UserDAO;

public class UserService implements IUserService {
	
	private UserDAO ud;
	private User user;
	private Employee em;
	
	public UserService(UserDAO ud) {
		this.ud = ud;
		this.user = null;
		this.em = null;
	}

	// This is the method in which the j unit test was written for 
	public User login(String username, String password) {
		User loggedInUser = null;
		try {
			loggedInUser = this.ud.findOneUser(username, password);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		this.user = loggedInUser;
		return loggedInUser;
	}

	public void logout() {
		return;
	}
	
	public Employee loginAsEmployee(String username, String password) {
		Employee loggedInEmployee = null;
		try {
			loggedInEmployee = this.ud.findEmployee(username, password);
		} catch (EmployeeNotFoundException e) {
			System.out.println("Employee was not found.");
		}
		
		this.em = loggedInEmployee;
		return loggedInEmployee;
	}

	public Employee getEm() {
		return em;
	}

	public void setEm(Employee em) {
		this.em = em;
	}

	public void createUserAccount(String username, String password) {
		// We need to call a method in the UserDAO to create new login details
		try {
			this.ud.createNewUser(username, password);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnableToCreateUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isValidUsername(String username) {
		User foundUser = this.ud.getOneUserByUsername(username);
		if(foundUser == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public User getCurrentUser() {
		return this.user;
	}
	
	public void setCurrentUser(User user) {
		this.user = user;
	}

	
}
