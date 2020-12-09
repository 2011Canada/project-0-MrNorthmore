package com.revature.menus;

import java.util.Scanner;

import com.revature.services.BankService;
import com.revature.services.UserService;

import java.util.List;

public class BankMenu implements Displayable {
	
	BankService bs;
	UserService us;
	
	// Scanner object responsible for getting user input
	Scanner userInput;
	
	public BankMenu(BankService bs, UserService us) {
		this.bs = bs;
		this.us = us;
		userInput = new Scanner(System.in);
	}
	
	
	public void displayMenuHeader() {
		System.out.println("Welcome to Northmore Financial.");
		System.out.println("-------------------------------");
		System.out.println("Please select one of the following options to get started.");
		System.out.println("1. Login using an existging account");
		System.out.println("2. Create a new account");
		String entryChoice = this.userInput.nextLine();
		
		if(Integer.parseInt(entryChoice) == 1) {
			this.loginMenu();
		} else if(Integer.parseInt(entryChoice) == 2) {
			this.createAccountMenu();
		} else {
			System.out.println("Please select a valid option.");
			this.displayMenuHeader();
		}
	}
	
	public void loginMenu() {
		System.out.println("Please login to get started.");
		System.out.println("Username: ");
		String usernameInput = this.userInput.nextLine();
		System.out.println("Password: ");
		String passwordInput = userInput.nextLine();
		boolean loginSuccessful = this.us.login(usernameInput, passwordInput);
		if(loginSuccessful) {
			// we can move user to the next menu as they are logged in
			System.out.println("Login was successful.");
			System.out.println("----------------------");
			this.displayMainMenu();
		} else {
			// The login was not successful therefore try again
			System.out.println("Sorry, we are unable to login. Please try again.");
			this.loginMenu();
		}
	}
	
	public void displayMainMenu() {
		System.out.println("How may we assist you?");
		System.out.println("1. Apply for new bank account");
		System.out.println("2. View Balance");
		System.out.println("3. Deposit");
		System.out.println("4. Withdrawl");
		String userChoice = this.userInput.nextLine();
		int integerChoice = Integer.parseInt(userChoice);
		if(integerChoice < 1 || integerChoice > 4) {
			System.out.println("Invalid. Please select a valid option");
			this.displayMainMenu();
		} else if(integerChoice == 1) {
			// User wants to apply for a new account
		} else if(integerChoice == 2) {
			// User wants to view balances of accounts
		} else if(integerChoice == 3) {
			// User wants to deposit money into account
		} else if(integerChoice == 4) {
			// User wants to withdraw money from account
		}
	}
	
	
	public void createAccountMenu() {
		
	}
	
	/*
	 * This handleUserInput method gets the next line of user input, checks for
	 * a number out of bounds or a catches an exception if a non-integer value is entered
	 * 
	 * @return
	 * 			void
	 */
//	public void handleUserInput() {
//		String input = this.userInput.nextLine();
//		
//		try {
//			int numericalInput = Integer.parseInt(input);
//			if(numericalInput < 0 || numericalInput >= lines.size()) {
//				System.out.println("Sorry, please choose a valid option");
//				return;
//			}
//		} catch(NumberFormatException e) {
//			System.out.println("Sorry, please choose a valid option");
//		}
//		
//		
//	}


	public String display() {
		// TODO Auto-generated method stub
		return null;
	}

}
