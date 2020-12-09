package com.revature.exceptions;

public class InvalidAccountException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidAccountException() {
		super("Sorry, this account is invalid. Please select another.");
	}
}
