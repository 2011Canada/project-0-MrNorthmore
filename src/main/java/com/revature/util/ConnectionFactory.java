package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// a class that builds connection objects based on configuration
public class ConnectionFactory {
	
	// turn the factory into a singleton so only the factory can produce a connection
	// to do this we make a private static reference to ourself -> one and only copy
	
	private static ConnectionFactory cf = new ConnectionFactory(1);
	
	//provide a single point of access to connection factory
	public static ConnectionFactory getConnectionFactory() {
		return cf;
	}
	
	// List of all the DB connections to allow for multiple
	private Connection [] dbConnection;
	
	// Private connection factory constructor for ensuring singleton pattern
	private ConnectionFactory(int numOfConnections ) {
		
		String url = System.getenv("DB_URL");
		String user = System.getenv("DB_USER");
		String password = System.getenv("DB_PASSWORD");
		
		
		try {
			this.dbConnection = new Connection[numOfConnections];
			for(int i=0; i<this.dbConnection.length; i++) {
				Connection conn = DriverManager.getConnection(url, user, password);
				this.dbConnection[i] = conn;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public Connection getConnection() {
		// TODO
		return this.dbConnection[0];
	}
	
	public void releaseConnection(Connection conn) {
		// TODO
		return;
	}

}
