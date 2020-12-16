package com.revature.menus;

import java.util.Scanner;

import com.revature.exceptions.InsufficientFundsException;
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

	// Service objects to give access to the services
	BankService bs;
	UserService us;

	// Scanner object for user input
	Scanner userInput;

	public BankMenu(BankService bs, UserService us) {
		this.bs = bs;
		this.us = us;
		userInput = new Scanner(System.in);
	}

	public void displayMenu() {
		BankLauncher.bankLogger.info("[DISPLAY MENU]: Start Menu");
		System.out.println("\n-------------------------------");
		System.out.println("Welcome to Northmore Financial.");
		System.out.println("-------------------------------\n");
		System.out.println("Please select one of the following options to get started.");
		System.out.println("1. New User");
		System.out.println("2. Customer Login");
		System.out.println("3. Employee Login");
		System.out.println("4. Exit");
		String entryChoice = this.userInput.nextLine();
		BankLauncher.bankLogger.info("[DISPLAY MENU]: User entered: " + entryChoice);
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
			BankLauncher.bankLogger.info("[MENU]: User selected invalid option.");
			System.out.println("Please select a valid option.\n");
		}
	}

	public void createAccountMenu() {
		BankLauncher.bankLogger.info("[CREATE ACCOUNT MENU]: New User Account Menu");
		System.out.println("\n-------------------------");
		System.out.println("New User Menu");
		System.out.println("-------------------------\n");
		System.out.println("Please enter username: ");
		String usernameInput = this.userInput.nextLine();
		BankLauncher.bankLogger.info("[CREATE ACCOUNT MENU]:User entered: " + usernameInput);
		if (usernameInput.equals("") || usernameInput == null) {
			System.out.println("Please enter a valid username");
			this.createAccountMenu();
			return;
		}
		// Here we need to check if user name already exists
		Boolean isUsernameValid = this.us.isValidUsername(usernameInput);
		if (!isUsernameValid) {
			BankLauncher.bankLogger.info("[CREATE ACCOUNT MENU]:Username already exists");
			System.out.println("Sorry, this user already exists. Please select a different username. \n");
			this.createAccountMenu();
			return;
		}
		System.out.println("\nPlease enter a password: ");
		String passwordInput = this.userInput.nextLine();
		if (passwordInput.equals("") || passwordInput == null) {
			BankLauncher.bankLogger.info("[CREATE ACCOUNT MENU]: Invalid password");
			System.out.println("Please enter a valid password");
			this.createAccountMenu();
			return;
		}
		this.us.createUserAccount(usernameInput, passwordInput);
		System.out.println("\nAccount has been created. Please login.");
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
			BankLauncher.bankLogger.info("[LOGIN MENU]: Invalid username");
			System.out.println("Please enter a valid username");
			this.loginMenu();
			return;
		}

		System.out.println("Password: ");
		String passwordInput = this.userInput.nextLine();
		System.out.println("\n");
		if (passwordInput.equals("") || passwordInput == null) {
			BankLauncher.bankLogger.info("[LOGIN MENU]: Invalid password");
			System.out.println("Please enter a valid password");
			this.loginMenu();
			return;
		}
		User loggedInUser = this.us.login(usernameInput, passwordInput);
		if (loggedInUser != null) {
			// we can move user to the next menu as they are logged in
			BankLauncher.bankLogger.info("[LOGIN MENU]: Login Successful");
			System.out.println("Login was successful.");
			System.out.println("----------------------");
			System.out.println("Currently Logged in with user Id: ");
			System.out.println(loggedInUser.getUserId());
			System.out.println("----------------------");
			BankLauncher.bankLogger.info("[LOGIN MENU]: Going to main menu");
			this.displayMainMenu();
			return;
		} else {
			// The login was not successful therefore try again
			System.out.println("Sorry, we are unable to login. Please try again.");
			BankLauncher.bankLogger.info("[LOGIN MENU]: Login failed");
			this.loginMenu();
			return;
		}
	}

	public void displayMainMenu() {
		BankLauncher.bankLogger.info("[CUSTOMER MAIN MENU]: Entered main customer menu");
		System.out.println("\n----------------------------");
		System.out.println("Main Menu");
		System.out.println("----------------------------\n");
		System.out.println("How may we assist you?");
		System.out.println("1. New Account");
		System.out.println("2. View Accounts");
		System.out.println("3. Deposit/Withdrawl");
		System.out.println("4. Transfers");
		System.out.println("5. Logout");
		String userChoice = this.userInput.nextLine();
		BankLauncher.bankLogger.info("[CUSTOMER MAIN MENU]: User entered: " + userChoice);
		try {
			int integerChoice = Integer.parseInt(userChoice);
			if (integerChoice < 1 || integerChoice > 5) {
				System.out.println("Error: Please select a valid option.");
				this.displayMainMenu();
				return;
			} else if (integerChoice == 1) {
				// User wants to apply for a new account
				this.applyForAccountMenu();
				return;
			} else if (integerChoice == 2) {
				// User wants to view balances of accounts
				this.balancesMenu();
				return;
			} else if (integerChoice == 3) {
				// User wants to deposit money into account
				this.depositWithdrawlMenu();
				this.displayMainMenu();
				return;
			} else if (integerChoice == 4) {
				// User wants to transfer money from account to account
				this.transferMenu();
				this.displayMainMenu();
				return;
			} else if (integerChoice == 5) {
				// User wants to logout
				BankLauncher.bankLogger.info("[CUSTOMER MAIN MENU]: User is logging out");
				System.out.println("Bye for now\n");
				return;	
			}
		} catch (NumberFormatException e) {
			BankLauncher.bankLogger.info("[CUSTOMER MAIN MENU]: User entered non numerical data");
			System.out.println("Please select a valid option.\n");
			this.displayMainMenu();
			return;
		}

	}

	public void balancesMenu() {
		BankLauncher.bankLogger.info("[MENU]: View Account Menu");
		List<Account> accountsToDisplay = this.bs.getAllUsersAccounts(this.us.getCurrentUser());
		System.out.println("\n------------------------");
		System.out.println("Accounts & Balances");
		System.out.println("------------------------\n");
		BankLauncher.bankLogger.info("[MENU]: Accounts: " + accountsToDisplay.toString());
		for (Account a : accountsToDisplay) {
			System.out.println("Account: "
					+ String.valueOf(a.getAccountNumber() + "  |  Balance: " + String.valueOf(a.getBalance())));
		}
		System.out.println("------------------------");
		System.out.println("Press enter to continue...");
		this.userInput.nextLine();
		this.displayMainMenu();

	}

	public void depositWithdrawlMenu() {
		BankLauncher.bankLogger.info("[WITH/DEP MENU]: Withdraw Deposit Menu");
		System.out.println("\n------------------------");
		System.out.println("Withdraw / Deposit Menu");
		System.out.println("------------------------\n");
		System.out.println("What would you like to do?");
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("Or press enter to return...");
		String depOrWit = this.userInput.nextLine();
		if(depOrWit.equals("")) {
			return;
		}
		int opChoice;
		try {
			opChoice = Integer.parseInt(depOrWit);
			if(opChoice > 2 || opChoice < 1) {
				System.out.println("Please enter a valid selection.");
				this.depositWithdrawlMenu();
				return;
			}
			List<Account> accounts = this.bs.getAllUsersAccounts(this.us.getCurrentUser());
			if(accounts.size() < 1) {
				System.out.println("Please create an account and get approval before transacting.");
				return;
			}
			System.out.println("Which account would you like?");
			for (int i = 0; i < accounts.size(); i++) {
				System.out.println("Account #: " + String.valueOf(i) + ". " + accounts.get(i).getAccountNumber() + "  |  Balance: "
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
				BankLauncher.bankLogger.info("[WITH/DEP MENU]: User selected account " + chosenAccount.toString());
				System.out.println("You selected: \nAccount: " + chosenAccount.getAccountNumber() + "  |  Balance: "
						+ chosenAccount.getBalance());
				System.out.println("How much? ");
				String depositAmount = this.userInput.nextLine();
				Double deposit = Double.parseDouble(depositAmount);
				// We use this max value as our database type is numeric(10,2) 
				if(deposit < 0.00 || deposit > 99999999.99 ) {
					BankLauncher.bankLogger.info("[WITH/DEP MENU]: User entered invalid amount");
					throw new NumberFormatException();
				} else if(opChoice == 2)
				if(opChoice == 1) {
					BankLauncher.bankLogger.info("[WITH/DEP MENU]: Proceeding with deposit");
					this.bs.depositOrWithdraw(chosenAccount, deposit, "deposit");
				} else if(opChoice == 2) {
					if(deposit > chosenAccount.getBalance()) {
						throw new InsufficientFundsException();
					}
					BankLauncher.bankLogger.info("[WITH/DEP MENU]: Proceeding with withdrawl");
					this.bs.depositOrWithdraw(chosenAccount, deposit, "withdraw");
				}
				return;
			} catch(NumberFormatException e) {
				System.out.println("Please enter a valid numeric selection.");
				this.depositWithdrawlMenu();
				return;
			} catch (InsufficientFundsException e) {
				System.out.println("Sorry you have insufficient funds.");
				this.depositWithdrawlMenu();
				return;
			}
		} catch(NumberFormatException e) {
			System.out.println("Please enter a valid selection.");
			this.depositWithdrawlMenu();
			return;
		}
		
		
	}

	public void applyForAccountMenu() {
		BankLauncher.bankLogger.info("[NEW BANK ACCOUNT MENU]: New Account Application Menu");
		System.out.println("Welcome to the account application menu");
		System.out.println("------------------------");
		System.out.println("How much would you like to initially deposit? ");
		String initialBalanceInput = this.userInput.nextLine();
		BankLauncher.bankLogger.info("[NEW BANK ACCOUNT MENU]: User wants to deposit " + initialBalanceInput);
		try {
			Double initialBalance = Double.parseDouble(initialBalanceInput);
			if(initialBalance > 99999999.99 || initialBalance < 0) {
				System.out.println("Sorry, please select a valid amount");
				this.applyForAccountMenu();
				return;
			}
			BankLauncher.bankLogger.info("[NEW BANK ACCOUNT MENU]: Proceeding with application");
			this.bs.applyForAccount(this.us.getCurrentUser(), initialBalance);
			System.out.println("Your request is pending approval.");
			System.out.println("------------------------");
			this.displayMainMenu();
			return;
		} catch(NumberFormatException e) {
			System.out.println("Please enter a valid numerical balance");
			this.applyForAccountMenu();
			return;
		}
		
	}

	public void employeeLoginMenu() {
		BankLauncher.bankLogger.info("[EMPLOYEE LOGIN MENU]: Employee Login Menu");
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
			BankLauncher.bankLogger.info("[EMPLOYEE LOGIN MENU]: Employee Login Successful");
			this.employeeMainMenu();
		} else {
			System.out.println("Sorry, we are unable to login. Please try again.");
			this.employeeLoginMenu();
			return;
		}
	}

	public void employeeMainMenu() {
		BankLauncher.bankLogger.info("[EMPLOYEE MAIN MENU]: Employee Main Menu");
		System.out.println("\n------------------------");
		System.out.println("Employee Main Menu");
		System.out.println("------------------------\n");
		System.out.println("1. Search for customer");
		System.out.println("2. Logout");
		String employeeOperation = this.userInput.nextLine();
		
		try {
			int emOP = Integer.parseInt(employeeOperation);
			if(emOP > 2 || emOP < 1) {
				throw new NumberFormatException();
			} else if(emOP == 2) {
				BankLauncher.bankLogger.info("[EMPLOYEE MAIN MENU]: Employee logging out");
				System.out.println("Bye for now");
				return;
			}
		} catch(NumberFormatException e) {
			BankLauncher.bankLogger.info("[EMPLOYEE MAIN MENU]: Invalid operation chosen.");
			this.employeeMainMenu();
			return;
		}
		
		System.out.println("Enter customer id: ");
		String selectedCustomer = this.userInput.nextLine();
		System.out.println("\n");
		
		try {
			int customerToSearch = Integer.parseInt(selectedCustomer);
			List<Account> customerAccounts = this.bs.getAllAccountsByUserId(customerToSearch);

			// If the user has no accounts yet, we want to display a message and return
			if (customerAccounts.size() < 1) {
				System.out.println("Sorry this user has no accounts yet. Please have the user apply for a new account");
				this.employeeMainMenu();
				return;
			} else {
				System.out.println("\n***************");
				System.out.println("Accounts: ");
				System.out.println("***************\n");
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
				BankLauncher.bankLogger.info("[EMPLOYEE MAIN MENU]: Selected Account for approval: " + customerAccounts.get(selection).getAccountNumber());
				this.bs.approveAccount(customerAccounts.get(selection).getAccountNumber());
				System.out.println("The account has been approved.");
				this.employeeMainMenu();
				return;

			} else if (operation == 2) {
				// The employee is looking for accounts transaction log
				List<Transaction> transactions = this.bs
						.getAllAccountTransactions(customerAccounts.get(selection).getAccountNumber());
				System.out.println("Transactions: ");
				for (int i = 0; i < transactions.size(); i++) {
					System.out.println(transactions.get(i).getAmount() + "  |  " + transactions.get(i).getOperation());
				}
				System.out.println("Press enter to continue...");
				this.userInput.nextLine();
				this.employeeMainMenu();
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("Please enter a valid value .\n");
			this.employeeMainMenu();
			return;
		}
	}

	public void transferMenu() {
		BankLauncher.bankLogger.info("[TRANSFER MENU]: Transfer Menu");
		System.out.println("\n----------------------------");
		System.out.println("Transfers Menu");
		System.out.println("----------------------------\n");
		System.out.println("What would you like to do? ");
		System.out.println("1. View transfers");
		System.out.println("2. New Transfer");
		System.out.println("Or press enter to return...");
		String selection = this.userInput.nextLine();
		int numSelection = Integer.parseInt(selection);
		if (numSelection == 1) {
			List<Account> accounts = this.bs.getAllUsersAccounts(this.us.getCurrentUser());
			List<Transfer> allTransfers = new ArrayList<Transfer>();
			if(allTransfers.size() > 0) {
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
					this.transferMenu();
					return;
				}
			} else {
				System.out.println("You currently have no pending transfers.");
				this.transferMenu();
				return;
			}
		} else if (numSelection == 2) {
			List<Account> accounts = this.bs.getAllUsersAccounts(this.us.getCurrentUser());
			if(accounts.size()>0) {
				System.out.println("Accounts: ");
				for (int i = 0; i < accounts.size(); i++) {
					System.out.println(String.valueOf(i) + ". " + accounts.get(i).getAccountNumber() + "  |  Balance: "
							+ accounts.get(i).getBalance());
				}
				System.out.println("Choose a withdrawl account: ");
				String fromAcct = this.userInput.nextLine();
				try {
					int accountSelection = Integer.parseInt(fromAcct);
					if(accountSelection < 0 || accountSelection > accounts.size()-1) {
						System.out.println("Please make a valid selection.");
						this.transferMenu();
						return;
					}
					Account chosenAcct = accounts.get(accountSelection);
					System.out.println("Which account would you like to send it to?");
					String toAccount = this.userInput.nextLine();
					int toAcctNumber = Integer.parseInt(toAccount);
					if(!this.bs.isValidAccountNumber(toAcctNumber)) {
						System.out.println("Invalid account number. Please try again.");
						this.transferMenu();
						return;
					}
					System.out.println("How much would you like to send? ");
					String amt = this.userInput.nextLine();
					Double amount = Double.parseDouble(amt);
					if(chosenAcct.getBalance() < amount) {
						throw new InsufficientFundsException();
					} else if(amount < 0.01 || amount > 99999999.99) {
						System.out.println("Please enter a valid amount");
					}
					this.bs.postTransfer(chosenAcct, toAcctNumber, amount);
				} catch(NumberFormatException e) {
					System.out.println("Please enter a valid input.");
					this.transferMenu();
					return;
				} catch (InsufficientFundsException e) {
					System.out.println("Sorry you have insufficient funds. Choose a lower value.");
					this.transferMenu();
					return;
				}
			} else {
				System.out.println("Please create an account and get it approved before initiating transfers.");
				return;
			}
		}
	}

}
