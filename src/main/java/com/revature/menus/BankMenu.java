package com.revature.menus;

import java.util.Scanner;

import com.revature.launcher.BankLauncher;
import com.revature.models.Account;
import com.revature.models.Employee;
import com.revature.models.Transaction;
import com.revature.models.Transfer;
import com.revature.models.User;
import com.revature.services.BankService;
import com.revature.services.UserService;

import java.util.ArrayList;
import java.util.List;

public class BankMenu {

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
		BankLauncher.bankLogger.info("[MENU]: Start Menu");
		System.out.println("-------------------------------");
		System.out.println("Welcome to Northmore Financial.");
		System.out.println("-------------------------------\n");
		System.out.println("Please select one of the following options to get started.");
		System.out.println("1. Create a new account");
		System.out.println("2. Customer Login");
		System.out.println("3. Employee Login");
		System.out.println("4. Exit");
		String entryChoice = this.userInput.nextLine();
		try {
			int choice = Integer.parseInt(entryChoice);
			if (choice < 1 || choice > 4) {
				System.out.println("Please select a valid option.\n");
				return;
			}
			if (choice == 1) {
				this.createAccountMenu();
			} else if (choice == 2) {
				this.loginMenu();
			} else if (choice == 3) {
				this.employeeLoginMenu();
			} else if (choice == 4) {
				BankLauncher.bankLogger.info("[MENU]: User is exiting application");
				System.out.println("Thank you. Have a nice day!");
				System.exit(0);
			}
		} catch (NumberFormatException e) {
			System.out.println("Please select a valid option.\n");
		}
	}

	public void createAccountMenu() {
		BankLauncher.bankLogger.info("[MENU]: New User Account Menu");
		System.out.println("Create an Account");
		System.out.println("Please enter username: ");
		String usernameInput = this.userInput.nextLine();
		if (usernameInput.equals("") || usernameInput == null) {
			System.out.println("Please enter a valid username");
			this.createAccountMenu();
			return;
		}
		// Here we need to check if user name already exists
		Boolean isUsernameValid = this.us.isValidUsername(usernameInput);
		if (!isUsernameValid) {
			System.out.println("Sorry, this user already exists. Please select a different username. \n");
			this.createAccountMenu();
			return;
		}
		System.out.println("Please enter a password: ");
		String passwordInput = this.userInput.nextLine();
		if (passwordInput.equals("") || passwordInput == null) {
			System.out.println("Please enter a valid password");
			this.createAccountMenu();
			return;
		}
		this.us.createUserAccount(usernameInput, passwordInput);
		return;
	}

	public void loginMenu() {
		BankLauncher.bankLogger.info("[MENU]: Customer Login Menu");
		System.out.println("\n----------------------------");
		System.out.println("Login Menu");
		System.out.println("----------------------------\n");
		System.out.println("Username: ");
		String usernameInput = this.userInput.nextLine();
		System.out.println("\n");
		if (usernameInput.equals("") || usernameInput == null) {
			System.out.println("Please enter a valid username");
			this.loginMenu();
			return;
		}

		System.out.println("Password: ");
		String passwordInput = this.userInput.nextLine();
		System.out.println("\n");
		User loggedInUser = this.us.login(usernameInput, passwordInput);
		if (loggedInUser != null) {
			// we can move user to the next menu as they are logged in
			System.out.println("Login was successful.");
			System.out.println("----------------------");
			System.out.println("Currently Logged in with user Id: ");
			System.out.println(loggedInUser.getUserId());
			System.out.println("----------------------");
			this.displayMainMenu();
			return;
		} else {
			// The login was not successful therefore try again
			System.out.println("Sorry, we are unable to login. Please try again.");
			this.loginMenu();
		}
	}

	public void displayMainMenu() {
		BankLauncher.bankLogger.info("[MENU]: New User Account Menu");
		System.out.println("How may we assist you?");
		System.out.println("1. Apply for new bank account");
		System.out.println("2. View Balance");
		System.out.println("3. Deposit");
		System.out.println("4. Withdrawl");
		System.out.println("5. Transfer Funds");
		System.out.println("6. Exit");
		String userChoice = this.userInput.nextLine();

		try {
			int integerChoice = Integer.parseInt(userChoice);
			if (integerChoice < 1 || integerChoice > 6) {
				System.out.println("Please select a valid option.");
				this.displayMainMenu();
				return;
			} else if (integerChoice == 1) {
				// User wants to apply for a new account
				this.applyForAccountMenu();
			} else if (integerChoice == 2) {
				// User wants to view balances of accounts
				this.balancesMenu();
			} else if (integerChoice == 3) {
				// User wants to deposit money into account
				this.depositWithdrawlMenu();
			} else if (integerChoice == 4) {
				// User wants to withdraw money from account
				this.withdrawlMenu();
			} else if (integerChoice == 5) {
				// User wants to transfer money
				this.transferMenu();
			} else if (integerChoice == 6) {
				// User wants to exit
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("Please select a valid option.\n");
			this.displayMainMenu();
			return;
		}

	}

	public void balancesMenu() {
		BankLauncher.bankLogger.info("[MENU]: View Account Menu");
		List<Account> accountsToDisplay = this.bs.getAllUsersAccounts(this.us.getCurrentUser());
		System.out.println(accountsToDisplay.get(0));
		System.out.println("\n------------------------");
		System.out.println("Accounts & Balances");
		System.out.println("------------------------\n");
		for (Account a : accountsToDisplay) {
			System.out.println("Account: "
					+ String.valueOf(a.getAccountNumber() + "  |  Balance: " + String.valueOf(a.getBalance())));
		}
		System.out.println("------------------------");
		this.displayMainMenu();

	}

	public void depositWithdrawlMenu() {
		BankLauncher.bankLogger.info("[MENU]: Deposit Menu");
		System.out.println("------------------------\n");
		System.out.println("Withdraw / Deposit Menu");
		System.out.println("------------------------\n");
		System.out.println("What would you like to do?");
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		String depOrWit = this.userInput.nextLine();
		int opChoice;
		try {
			opChoice = Integer.parseInt(depOrWit);
			if(opChoice > 2 || opChoice < 1) {
				System.out.println("Please enter a valid selection.");
				this.depositWithdrawlMenu();
				return;
			}
			System.out.println("Which account would you like?");
			List<Account> accounts = this.bs.getAllUsersAccounts(this.us.getCurrentUser());
			for (int i = 0; i < accounts.size(); i++) {
				System.out.println(String.valueOf(i) + ". " + accounts.get(i).getAccountNumber() + "  |  Balance: "
						+ accounts.get(i).getBalance());
			}
			String accountSelection = this.userInput.nextLine();
			try {
				int selection = Integer.parseInt(accountSelection);
				if(selection < 0 || selection > accounts.size()-1) {
					System.out.println("Please enter a valid selection.");
					this.depositWithdrawlMenu();
					return;
				}
				Account chosenAccount = accounts.get(selection);
				System.out.println("You selected: \nAccount: " + chosenAccount.getAccountNumber() + "  |  Balance: "
						+ chosenAccount.getBalance());
				System.out.println("How much would you like to deposit? ");
				String depositAmount = this.userInput.nextLine();
				Double deposit = Double.parseDouble(depositAmount);
				if(opChoice == 1) {
					this.bs.depositOrWithdraw(chosenAccount, deposit, "deposit");
				} else if(opChoice == 2) {
					this.bs.depositOrWithdraw(chosenAccount, deposit, "withdraw");
				}
				return;
			} catch(NumberFormatException e) {
				System.out.println("Please enter a valid numeric selection.");
				this.depositWithdrawlMenu();
				return;
			}
		} catch(NumberFormatException e) {
			System.out.println("Please enter a valid selection.");
			this.depositWithdrawlMenu();
			return;
		}
		
		
	}

	public void withdrawlMenu() {
		System.out.println("Welcome to the withdrawl menu");
		System.out.println("------------------------");
	}

	public void applyForAccountMenu() {
		BankLauncher.bankLogger.info("[MENU]: New Account Application Menu");
		System.out.println("Welcome to the account application menu");
		System.out.println("------------------------");
		System.out.println("How much would you like to initially deposit? ");
		String initialBalanceInput = this.userInput.nextLine();
		try {
			Double initialBalance = Double.parseDouble(initialBalanceInput);
			if(initialBalance > 99999999.99) {
				System.out.println("Sorry, please select a lower balance");
				this.applyForAccountMenu();
				return;
			}
			this.bs.applyForAccount(this.us.getCurrentUser(), initialBalance);
			System.out.println("Your request is pending approval.");
			System.out.println("------------------------");
			this.displayMainMenu();
		} catch(NumberFormatException e) {
			System.out.println("Please enter a valid numerical balance");
			this.applyForAccountMenu();
			return;
		}
		
	}

	public void employeeLoginMenu() {
		BankLauncher.bankLogger.info("[MENU]: Employee Login Menu");
		System.out.println("\n------------------------");
		System.out.println("Employee Login Menu");
		System.out.println("------------------------\n");
		System.out.println("Username: ");
		String employeeInput = this.userInput.nextLine();
		System.out.println("\n");

		if (employeeInput.equals("") || employeeInput == null) {
			System.out.println("Please enter a valid username.\n");
			this.employeeLoginMenu();
			return;
		}

		System.out.println("Password: ");
		String passwordInput = this.userInput.nextLine();
		System.out.println("\n");

		if (passwordInput.equals("") || passwordInput == null) {
			System.out.println("Please enter a valid password.\n");
			this.employeeLoginMenu();
			return;
		}

		Employee e = this.us.loginAsEmployee(employeeInput, passwordInput);
		if (e != null) {
			this.employeeMainMenu();
		} else {
			System.out.println("Sorry, we are unable to login. Please try again.");
			this.employeeLoginMenu();
			return;
		}
	}

	public void employeeMainMenu() {
		BankLauncher.bankLogger.info("[MENU]: Employee Main Menu");
		System.out.println("\n------------------------");
		System.out.println("Employee Main Menu");
		System.out.println("------------------------\n");
		System.out.println("Enter customer id: ");
		String selectedCustomer = this.userInput.nextLine();

		try {
			int customerToSearch = Integer.parseInt(selectedCustomer);
			List<Account> customerAccounts = this.bs.getAllAccountsByUserId(customerToSearch);

			// If the user has no accounts yet, we want to display a message and return
			if (customerAccounts.size() < 1) {
				System.out.println("Sorry this user has no accounts yet. Please have the user apply for a new account");
				this.employeeMainMenu();
				return;
			} else {
				System.out.println("Accounts: ");
				for (int i = 0; i < customerAccounts.size(); i++) {
					System.out.println(String.valueOf(i) + ". " + customerAccounts.get(i).getAccountNumber()
							+ "  |  Balance: " + customerAccounts.get(i).getBalance());
				}
				System.out.println("Choose an account: ");
			}

			String accountSelection = this.userInput.nextLine();
			int selection = Integer.parseInt(accountSelection);
			if(selection < 0 || selection > customerAccounts.size()-1) {
				System.out.println("Please select a valid account.");
				this.employeeMainMenu();
				return;
			}
			System.out.println("What would you like to do with the account?");
			System.out.println("1. Approve Account");
			System.out.println("2. View Transaction Log");
			String operationSelection = this.userInput.nextLine();
			int operation = Integer.parseInt(operationSelection);
			if(operation < 1 || operation > 2) {
				System.out.println("Please select a valid option");
				this.employeeMainMenu();
				return;
			}
			if (operation == 1) {
				// The employee is looking to approve an account
				this.bs.approveAccount(customerAccounts.get(selection).getAccountNumber());
				System.out.println("The account has been approved.");
				this.employeeMainMenu();

			} else if (operation == 2) {
				// The employee is looking for accounts transaction log
				List<Transaction> transactions = this.bs
						.getAllAccountTransactions(customerAccounts.get(selection).getAccountNumber());
				System.out.println("Transactions: ");
				for (int i = 0; i < transactions.size(); i++) {
					System.out.println(transactions.get(i).getAmount() + "  |  " + transactions.get(i).getOperation());
				}
				System.out.println("Press any key to continue...");
				this.userInput.nextLine();
				this.employeeMainMenu();

			}
		} catch (NumberFormatException e) {
			System.out.println("Please enter a valid value .\n");
			this.employeeMainMenu();
			return;
		}
	}

	public void transferMenu() {
		BankLauncher.bankLogger.info("[MENU]: Transfer Menu");
		System.out.println("Welcome to the transfer menu");
		System.out.println("----------------------------");
		System.out.println("What would you like to do? ");
		System.out.println("1. View transfers");
		System.out.println("2. New Transfer");
		String selection = this.userInput.nextLine();
		int numSelection = Integer.parseInt(selection);
		if (numSelection == 1) {
			List<Account> accounts = this.bs.getAllUsersAccounts(this.us.getCurrentUser());
			List<Transfer> allTransfers = new ArrayList<Transfer>();
			System.out.println("Transfers: ");
			for (int i = 0; i < accounts.size(); i++) {
				List<Transfer> transfers = this.bs.getTransfersToAccount(accounts.get(i).getAccountNumber());
				for(Transfer t : transfers) {
					allTransfers.add(t);
				}
			}
			for(int i=0; i<allTransfers.size(); i++) {
				System.out.println(i + ". From:" + allTransfers.get(i).getFromAccount() + "  To: " + allTransfers.get(i).getToAccount());
			}
			System.out.println("Choose a transfer to accept or hit enter to return: ");
			String acceptOrReturn = this.userInput.nextLine();
			if(acceptOrReturn.equals("")) {
				return;
			}
			try {
				Account toAccount = null;
				Account fromAccount = null;
				int acceptChoice = Integer.parseInt(acceptOrReturn);
				if (acceptChoice < 0 || acceptChoice > allTransfers.size()-1) {
					System.out.println("Please select a valid option.");
					this.transferMenu();
					return;
				}
				Transfer chosenTransfer = allTransfers.get(acceptChoice);
				System.out.println("Are you sure? (y/n)");
				String yn = this.userInput.nextLine();
				if(!yn.equals("y") || !yn.equals("Y")) {
					for(Account a : accounts) {
						if(a.getAccountNumber() == chosenTransfer.getToAccount()) {
							toAccount = a;
						} else if(a.getAccountNumber() == chosenTransfer.getFromAccount()) {
							fromAccount = a;
						}
					}
					this.bs.acceptTransfer(toAccount, allTransfers.get(acceptChoice));
				} else {
					this.transferMenu();
					return;
				}
			} catch(NumberFormatException e) {
				System.out.println("Please enter a valid input");
			}
			

		} else if (numSelection == 2) {
			List<Account> accounts = this.bs.getAllUsersAccounts(this.us.getCurrentUser());
			System.out.println("Accounts: ");
			for (int i = 0; i < accounts.size(); i++) {
				System.out.println(String.valueOf(i) + ". " + accounts.get(i).getAccountNumber() + "  |  Balance: "
						+ accounts.get(i).getBalance());
			}
			System.out.println("Choose a withdrawl account: ");
			String fromAcct = this.userInput.nextLine();
			try {
				int accountSelection = Integer.parseInt(fromAcct);
				Account chosenAcct = accounts.get(accountSelection);
				System.out.println("Which account would you like to send it to?");
				String toAccount = this.userInput.nextLine();
				int toAcctNumber = Integer.parseInt(toAccount);
				System.out.println("How much would you like to send? ");
				String amt = this.userInput.nextLine();
				Double amount = Double.parseDouble(amt);
				this.bs.postTransfer(chosenAcct, toAcctNumber, amount);
			} catch(NumberFormatException e) {
				System.out.println("Please enter a valid input.");
			}
			
		}
	}

}
