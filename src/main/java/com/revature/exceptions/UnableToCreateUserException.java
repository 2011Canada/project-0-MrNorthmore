package com.revature.exceptions;

public class UnableToCreateUserException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5360081220156473575L;

	public UnableToCreateUserException() {
		super("Unable to create user.");
	}
}
