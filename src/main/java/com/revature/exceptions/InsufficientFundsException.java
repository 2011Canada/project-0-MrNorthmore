package com.revature.exceptions;

public class InsufficientFundsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientFundsException() {
		super("Sorry, this account has insufficient funds");
	}
}
