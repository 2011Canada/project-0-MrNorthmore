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
	
	
	public void displayMenu() {
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
			this.displayMenu();
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
		System.out.println("5. Exit");
		String userChoice = this.userInput.nextLine();
		int integerChoice = Integer.parseInt(userChoice);
		if(integerChoice < 1 || integerChoice > 5) {
			System.out.println("Invalid. Please select a valid option");
			this.displayMainMenu();
		} else if(integerChoice == 1) {
			// User wants to apply for a new account
			this.applyForAccountMenu();
		} else if(integerChoice == 2) {
			// User wants to view balances of accounts
			this.balancesMenu();
		} else if(integerChoice == 3) {
			// User wants to deposit money into account
			this.depositMenu();
		} else if(integerChoice == 4) {
			// User wants to withdraw money from account
		}
	}
	
	public void createAccountMenu() {
		System.out.println("Create an Account");
		System.out.println("Please enter username: ");
		String usernameInput = this.userInput.nextLine();
		System.out.println("Please enter a password: ");
		String passwordInput = this.userInput.nextLine();
		this.us.createAccount();
	}
	
	public void balancesMenu() {
		System.out.println("Welcome to balances menu");
		System.out.println("------------------------");
	}
	
	public void depositMenu() {
		System.out.println("Welcome to the deposit menu");
		System.out.println("------------------------");
	}
	
	public void withdrawlMenu() {
		System.out.println("Welcome to the withdrawl menu");
		System.out.println("------------------------");
	}
	
	public void applyForAccountMenu() {
		System.out.println("Welcome to the account application menu");
		System.out.println("------------------------");
	}

	public String display() {
		// TODO Auto-generated method stub
		return null;
	}

}
