package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// a class that builds connection objects based on configuration
public class ConnectionFactory {
	
	// provide single static reference to connection factory enabling a singleton pattern
	
	private static ConnectionFactory cf = new ConnectionFactory(1);
	
	// single point of access to connection factory
	public static ConnectionFactory getConnectionFactory() {
		return cf;
	}
	
	// List of all the DB connections, allowing multiple connections
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
