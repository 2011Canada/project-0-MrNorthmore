package com.revature.exceptions;

public class UserNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1689381930415180837L;

	public UserNotFoundException() {
		super("Uhh Oh. User not found");
	}

}
