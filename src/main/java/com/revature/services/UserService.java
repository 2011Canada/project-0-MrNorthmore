package com.revature.services;

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

	public User login(String username, String password) {
		User loggedInUser = null;
		try {
			loggedInUser = this.ud.findOneUser(username, password);
			this.user = loggedInUser;
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		
		return this.user;
	}

	public void logout() {
		return;
	}
	
	public Employee loginAsEmployee(String username, String password) {
		Employee loggedInEmployee = null;
		loggedInEmployee = this.ud.findEmployee(username, password);
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
	
	public User getCurrentUser() {
		return this.user;
	}
}
