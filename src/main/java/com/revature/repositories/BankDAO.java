package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class BankDAO implements IBankDAO {

	private ConnectionFactory cf = ConnectionFactory.getConnectionFactory();

	public Account createOne(User user, Double initialBalance) {
		Connection conn = this.cf.getConnection();
		Account newAccount = null;

		try {

			// Create our SQL string with ? placeholder values
			String sql = "insert into accounts (balance, user_id, account_approved)"
					+ "	values (?, ?, false) returning \"account_num\";";

			// Create a prepared statement to fill with user values
			PreparedStatement insertAccount = conn.prepareStatement(sql);

			// Insert user name and password values into the statement
			insertAccount.setDouble(1, initialBalance);
			insertAccount.setInt(2, user.getUserId());

			ResultSet res = insertAccount.executeQuery();
			
			if(res.next()) {
				System.out.println("Account has been created and pending approval.");
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return newAccount;
	}

	public List<Account> getAllUserAccounts(User user) {
		Connection conn = this.cf.getConnection();

		List<Account> accountList = new ArrayList<Account>();

		try {

			// Create our SQL string with ? placeholder values
			String sql = "select * from accounts " + "where user_id = ?;";

			// Create a prepared statement to fill with user values
			PreparedStatement insertUser = conn.prepareStatement(sql);

			// Insert user name and password values into the statement
			insertUser.setInt(1, user.getUserId());

			ResultSet res = insertUser.executeQuery();

			while (res.next()) {
				Account currentAccount = new Account();
				currentAccount.setAccountNumber(res.getInt("account_num"));
				currentAccount.setAccountUser(res.getInt("user_id"));
				currentAccount.setBalance(res.getDouble("balance"));
				accountList.add(currentAccount);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accountList;
	}

	public User getOne() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Account updateBalance(Account account, Double amount) {
		Connection conn = this.cf.getConnection();
		Account depositAccount = account;

		try {

			// Create our SQL string with ? placeholder values
			String sql = "update \"accounts\" as a set balance = ? where account_num = ? returning \"account_num\", \"balance\", \"user_id\", \"account_approved\";";

			// Create a prepared statement to fill with user values
			PreparedStatement insertAccount = conn.prepareStatement(sql);

			// Insert user name and password values into the statement
			insertAccount.setDouble(1, amount);
			insertAccount.setInt(2, account.getAccountNumber());

			ResultSet res = insertAccount.executeQuery();
			
			if(res.next()) {
				depositAccount.setBalance(res.getDouble("balance"));
				System.out.println("Your balance is now: " + res.getDouble("balance"));
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return depositAccount;
	}
	
	public List<Account> getUsersAccountsById(int userId) {
		Connection conn = this.cf.getConnection();

		List<Account> accountList = new ArrayList<Account>();

		try {

			// Create our SQL string with ? placeholder values
			String sql = "select * from accounts " + "where user_id = ?;";

			// Create a prepared statement to fill with user values
			PreparedStatement insertUser = conn.prepareStatement(sql);

			// Insert user name and password values into the statement
			insertUser.setInt(1, userId);

			ResultSet res = insertUser.executeQuery();

			while (res.next()) {
				Account currentAccount = new Account();
				currentAccount.setAccountNumber(res.getInt("account_num"));
				currentAccount.setAccountUser(res.getInt("user_id"));
				currentAccount.setBalance(res.getDouble("balance"));
				accountList.add(currentAccount);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accountList;
	}
	
	public List<Transaction> findTransactionsByAccount(int accountNumber) {
		Connection conn = this.cf.getConnection();

		List<Transaction> transactionList = new ArrayList<Transaction>();
		
		try {

			// Create our SQL string with ? placeholder values
			String sql = "select * from transactions " + "where account_num = ?;";

			// Create a prepared statement to fill with user values
			PreparedStatement insertUser = conn.prepareStatement(sql);

			// Insert user name and password values into the statement
			insertUser.setInt(1, accountNumber);

			ResultSet res = insertUser.executeQuery();

			while (res.next()) {
				Transaction trans = new Transaction();
				trans.setOperation(res.getString("operation"));
				trans.setTransaction_id(res.getInt("transaction_id"));
				trans.setTransactionDate(res.getTimestamp("date_time"));
				trans.setAmount(res.getDouble("amount"));
				trans.setAssociatedAccount(res.getInt("account_num"));
				transactionList.add(trans);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		return transactionList;
	}
	
	public void insertTransaction() {
		
	}

}
