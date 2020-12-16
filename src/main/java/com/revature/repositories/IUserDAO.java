package com.revature.repositories;

import java.util.List;

import com.revature.exceptions.EmployeeNotFoundException;
import com.revature.exceptions.InternalErrorException;
import com.revature.exceptions.UnableToCreateUserException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Employee;
import com.revature.models.User;

public interface IUserDAO {
	/*
	 * This method will create new user login credentials 
	 * 
	 * @return
	 * 			the new user object created in the database
	 */
	public User createNewUser(String username, String password) throws UserNotFoundException, InternalErrorException, UnableToCreateUserException;
	
	public User findOneUser(String username, String password) throws UserNotFoundException;
	
	public List<User> findAll();
	
	public Employee findEmployee(String username, String password) throws EmployeeNotFoundException;
	
	public User getOneUserByUsername(String username);
}
