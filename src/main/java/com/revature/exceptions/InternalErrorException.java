package com.revature.exceptions;

public class InternalErrorException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4823350551985860901L;

	public InternalErrorException() {
		super("Theres been an internal error");
	}
}
