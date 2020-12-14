package com.revature.repositories;

import com.revature.exceptions.InternalErrorException;
import com.revature.exceptions.UnableToCreateUserException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;

public interface IUserDAO {
	/*
	 * This method will create new user login credentials 
	 * 
	 * @returns 
	 * 			the new user object created in the database
	 */
	public User createNewUser(String username, String password) throws UserNotFoundException, InternalErrorException, UnableToCreateUserException;
}
