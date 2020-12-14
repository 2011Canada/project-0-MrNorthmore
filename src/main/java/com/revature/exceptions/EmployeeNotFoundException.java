package com.revature.exceptions;

public class EmployeeNotFoundException extends Exception {

		/**
	 * 
	 */
	private static final long serialVersionUID = -985397105350986627L;

		public EmployeeNotFoundException() {
			super("Employee not found");
		}
}
