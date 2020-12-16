package com.revature.models;

import java.sql.Timestamp;

public class Transaction {
	
	private int transaction_id;
	private int associatedAccount;
	private String operation;
	private Timestamp transactionDate;
	private Double amount;
	
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Timestamp getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Timestamp timestamp) {
		this.transactionDate = timestamp;
	}
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	public int getAssociatedAccount() {
		return associatedAccount;
	}
	public void setAssociatedAccount(int i) {
		this.associatedAccount = i;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}

}
