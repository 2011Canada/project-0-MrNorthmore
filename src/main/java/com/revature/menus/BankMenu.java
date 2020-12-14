package com.revature.menus;

import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.Employee;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.services.BankService;
import com.revature.services.UserService;

import java.util.List;

public class BankMenu implements Displayable {
	
	// Service objects to give access to the user and bank services
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
		System.out.println("1. Login using existing customer account");
		System.out.println("2. Create a new account");
		System.out.println("3. Login as an employee");
		System.out.println("4. Exit");
		String entryChoice = this.userInput.nextLine();
		int choice = Integer.parseInt(entryChoice);
		
		if(choice == 1) {
			this.loginMenu();
		} else if(choice == 2) {
			this.createAccountMenu();
		} else if(choice == 3) {
			this.employeeLoginMenu();
		} else if(choice == 4) {
			System.exit(0);
		}
		
		else {
			System.out.println("Please select a valid option.");
			this.displayMenu();
		}
	}
	
	public void loginMenu() {
		System.out.println("Please login to get started.");
		System.out.println("Username: ");
		String usernameInput = this.userInput.nextLine();
		System.out.println("Password: ");
		String passwordInput = this.userInput.nextLine();
		User loggedInUser = this.us.login(usernameInput, passwordInput);
		if(loggedInUser != null ) {
			// we can move user to the next menu as they are logged in
			System.out.println("Login was successful.");
			System.out.println("----------------------");
			System.out.println("Currently Logged in with user Id: ");
			System.out.println(loggedInUser.getUserId());
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
			this.withdrawlMenu();
		} else if(integerChoice == 5) {
			// User wants to withdraw money from account
			return;
		}
	}
	
	public void createAccountMenu() {
		System.out.println("Create an Account");
		System.out.println("Please enter username: ");
		String usernameInput = this.userInput.nextLine();
		System.out.println("Please enter a password: ");
		String passwordInput = this.userInput.nextLine();
		this.us.createUserAccount(usernameInput, passwordInput);
	}
	
	public void balancesMenu() {
		List<Account> accountsToDisplay = this.bs.getAllUsersAccounts(this.us.getCurrentUser());
		System.out.println(accountsToDisplay.get(0));
		System.out.println("Welcome to balances menu");
		System.out.println("------------------------\n");
		System.out.println("Accounts/Balances: ");
		for(Account a: accountsToDisplay) {
			System.out.println("Account: " + String.valueOf(a.getAccountNumber() + "  |  Balance: " + String.valueOf(a.getBalance())));
		}
		System.out.println("------------------------");
		this.displayMainMenu();
		
	}
	
	public void depositMenu() {
		System.out.println("Welcome to the deposit menu");
		System.out.println("------------------------\n");
		System.out.println("Which account would you like?");
		List<Account> accounts = this.bs.getAllUsersAccounts(this.us.getCurrentUser());
		for(int i = 0; i < accounts.size(); i++) {
			System.out.println(String.valueOf(i) + ". " + accounts.get(i).getAccountNumber() + "  |  Balance: " + accounts.get(i).getBalance());
		}
		String accountSelection = this.userInput.nextLine();
		int selection = Integer.parseInt(accountSelection);
		Account chosenAccount = accounts.get(selection);
		System.out.println("Account selected: " + chosenAccount.getAccountNumber() + "  |  Balance: " + chosenAccount.getBalance());
		System.out.println("How much would you like to deposit? ");
		String depositAmount = this.userInput.nextLine();
		Double deposit = Double.parseDouble(depositAmount);
		this.bs.deposit(chosenAccount, deposit);
	}
	
	public void withdrawlMenu() {
		System.out.println("Welcome to the withdrawl menu");
		System.out.println("------------------------");
	}
	
	public void applyForAccountMenu() {
		System.out.println("Welcome to the account application menu");
		System.out.println("------------------------");
		System.out.println("How much would you like to initially deposit? ");
		String initialBalanceInput = this.userInput.nextLine();
		Double initialBalance = Double.parseDouble(initialBalanceInput);
		this.bs.applyForAccount(this.us.getCurrentUser(), initialBalance);
		System.out.println("------------------------");
		this.displayMainMenu();
	}
	
	public void employeeLoginMenu() {
		Employee e = null;
		System.out.println("Employee Login Menu");
		System.out.println("------------------------");
		System.out.println("Username: ");
		String employeeInput = this.userInput.nextLine();
		System.out.println("Password: ");
		String passwordInput = this.userInput.nextLine();
		e = this.us.loginAsEmployee(employeeInput, passwordInput);
		if(e != null) {
			this.employeeMainMenu();
		}
	}
	
	public void employeeMainMenu() {
		System.out.println("Employee Main Menu");
		System.out.println("------------------------");
		System.out.println("Enter customer id to get started: ");
		String selectedCustomer = this.userInput.nextLine();
		int customerToSearch = Integer.parseInt(selectedCustomer);
		List<Account> customerAccounts = this.bs.getAllAccountsByUserId(customerToSearch);
		System.out.println("Please select an account from the list below: ");
		for(int i = 0; i < customerAccounts.size(); i++) {
			System.out.println(String.valueOf(i) + ". " + customerAccounts.get(i).getAccountNumber() + "  |  Balance: " + customerAccounts.get(i).getBalance());
		}
		String accountSelection = this.userInput.nextLine();
		int selection = Integer.parseInt(accountSelection);
		System.out.println("What would you like to do with the account?");
		System.out.println("1. Approve Account");
		System.out.println("2. View Transaction Log");
		String operationSelection = this.userInput.nextLine();
		int operation = Integer.parseInt(operationSelection);
		if(operation == 1) {
			// The employee is looking to approve an account
			
		} else if(operation == 2) {
			// The employee is looking for accounts transaction log
			List<Transaction> transactions = this.bs.getAllAccountTransactions(customerAccounts.get(selection).getAccountNumber());
			for(int i=0; i<transactions.size(); i++) {
				System.out.println(transactions.get(i).getAmount() + "  |  " + transactions.get(i).getOperation());
			}
			
		}
	}

	public String display() {
		// TODO Auto-generated method stub
		return null;
	}

}
