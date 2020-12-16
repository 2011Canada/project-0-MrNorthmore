package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.Transfer;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class BankDAO implements IBankDAO {

	private ConnectionFactory cf = ConnectionFactory.getConnectionFactory();

	public Account createOne(User user, Double initialBalance) {
		Connection conn = this.cf.getConnection();
		Account newAccount = null;

		try {
			
			conn.setAutoCommit(false);

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
				String transSQL = "insert into transactions (account_num, operation, date_time, amount)\n"
						+ "	values (?, 'deposit', now(), ?) returning transaction_id;";
				PreparedStatement insertTransaction = conn.prepareStatement(transSQL);
				
				insertTransaction.setInt(1, res.getInt("account_num"));
				insertTransaction.setDouble(2, initialBalance);
				
				ResultSet transRes = insertTransaction.executeQuery();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.commit();
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cf.releaseConnection(conn);
		}

		return newAccount;
	}

	public List<Account> getAllUserAccounts(User user) {
		Connection conn = this.cf.getConnection();

		List<Account> accountList = new ArrayList<Account>();

		try {

			// Create our SQL string with ? placeholder values
			String sql = "select * from accounts " + "where user_id = ? and account_approved = true;";

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
	
	public Account updateBalance(Account account, Double amount, String operation, Double transAmt) {
		Connection conn = this.cf.getConnection();
		Account depositAccount = account;

		try {
			
			conn.setAutoCommit(false);

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
				
				String tSql = "insert into transactions (account_num, operation, date_time, amount)\n"
						+ "	values (?, ?, now(), ?) returning transaction_id;";
				
				PreparedStatement insertTrans = conn.prepareStatement(tSql);
				
				insertTrans.setInt(1, res.getInt("account_num"));
				insertTrans.setString(2, operation);
				insertTrans.setDouble(3, transAmt);
				
				ResultSet tRes = insertTrans.executeQuery();
				
				if(tRes.next()) {
					System.out.println("Your balance is now: " + res.getDouble("balance"));
				}
				
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.commit();
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cf.releaseConnection(conn);
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
		// TODO
	}
	
	public void approveAccount(int accountNum) {
		Connection conn = this.cf.getConnection();
		
		try {
			String sql = "update \"accounts\""
					+ "	set account_approved = true"
					+ "	where account_num = ?;";
			
			PreparedStatement updateAccount = conn.prepareStatement(sql);
			
			updateAccount.setInt(1, accountNum);
			
			updateAccount.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Transfer> getIncomingTransfersByAccountNumber(int accountNum) {
		
		Connection conn = this.cf.getConnection();
		List<Transfer> transferList = new ArrayList<Transfer>();
		
		try {
			String sql = "select * from transfers\n"
					+ "	where to_account = ?;";
			
			PreparedStatement getTransfers = conn.prepareStatement(sql);
			
			getTransfers.setInt(1, accountNum);
			
			ResultSet res = getTransfers.executeQuery();
			
			while(res.next()) {
				Transfer t = new Transfer();
				t.setAmount(res.getDouble("amount"));
				t.setFromAccount(res.getInt("from_account"));
				t.setToAccount(res.getInt("to_account"));
				t.setTransferId(res.getInt("transfer_id"));
				transferList.add(t);
			}
			
			return transferList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	public Transfer insertTransfer(Account fromAccount, int toAccountNum, Double amount) {
		Connection conn = this.cf.getConnection();
		Transfer t = null;
		try {
			
			conn.setAutoCommit(false);
			
			String sql = "insert into transfers (from_account, to_account, amount)"
					+ "	values (?, ?, ?) returning \"transfer_id\", \"from_account\",\"to_account\", \"amount\";";
			
			PreparedStatement newTransfer = conn.prepareStatement(sql);
			
			newTransfer.setInt(1, fromAccount.getAccountNumber());
			newTransfer.setInt(2, toAccountNum);
			newTransfer.setDouble(3, amount);
			
			ResultSet res = newTransfer.executeQuery();
			
			if(res.next()) {
				t = new Transfer();
				t.setFromAccount(res.getInt("from_account"));
				t.setToAccount(res.getInt("to_account"));
				t.setAmount(res.getDouble("amount"));
				t.setTransferId(res.getInt("transfer_id"));
				
				
				
				String sql2 = "update \"accounts\" as a set balance = ? where account_num = ? returning \"account_num\", \"balance\", \"user_id\", \"account_approved\";";
				
				PreparedStatement updateBalance = conn.prepareStatement(sql2);
				
				Double newBalance = fromAccount.getBalance() - t.getAmount();
				
				updateBalance.setDouble(1, newBalance);
				updateBalance.setInt(2, fromAccount.getAccountNumber());
				
				ResultSet balRes = updateBalance.executeQuery();
				
				if(balRes.next()) {
					String tSql = "insert into transactions (account_num, operation, date_time, amount)\n"
							+ "	values (?, ?, now(), ?) returning transaction_id;";
					
					PreparedStatement insertTrans = conn.prepareStatement(tSql);
					
					insertTrans.setInt(1, fromAccount.getAccountNumber());
					insertTrans.setString(2, "withdraw");
					insertTrans.setDouble(3, amount);
					
					ResultSet tRes = insertTrans.executeQuery();
					
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.commit();
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return t;
	}
	
	public boolean acceptTransfer(Account account, Transfer transfer) {
		
		Connection conn = this.cf.getConnection();

		try {
			
			conn.setAutoCommit(false);

			// Create our SQL string with ? placeholder values
			String sql = "update \"accounts\" as a set balance = ? where account_num = ? returning \"account_num\", \"balance\", \"user_id\", \"account_approved\";";

			// Create a prepared statement to fill with user values
			PreparedStatement updateAccount = conn.prepareStatement(sql);
			
			//Calculate new balance for query
			Double newBalance = account.getBalance() + transfer.getAmount();
			
			// Insert user name and password values into the statement
			updateAccount.setDouble(1, newBalance);
			updateAccount.setInt(2, account.getAccountNumber());

			ResultSet res = updateAccount.executeQuery();
			
			if(res.next()) {
				
				String tSql = "insert into transactions (account_num, operation, date_time, amount)\n"
						+ "	values (?, ?, now(), ?) returning transaction_id;";
				
				PreparedStatement insertTrans = conn.prepareStatement(tSql);
				
				insertTrans.setInt(1, res.getInt("account_num"));
				insertTrans.setString(2, "deposit");
				insertTrans.setDouble(3, transfer.getAmount());
				
				ResultSet tRes = insertTrans.executeQuery();
				
				if(tRes.next()) {
					String dSql = "delete from transfers\n"
							+ "	where transfer_id = ?;";
					
					PreparedStatement delTrans = conn.prepareStatement(dSql);
					
					delTrans.setInt(1, transfer.getTransferId());
					
					delTrans.executeUpdate();
					
					System.out.println("Your balance is now: " + res.getDouble("balance"));
				}
				
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.commit();
				conn.setAutoCommit(true);
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}

}
