package com.revature.launcher;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.menus.BankMenu;
import com.revature.repositories.BankDAO;
import com.revature.repositories.UserDAO;
import com.revature.services.BankService;
import com.revature.services.UserService;

public class BankLauncher {

	public static Scanner userInput;
	
	public static Logger bankLogger = LogManager.getLogger("com.revature.bank_logger");

	public static void main(String[] args) {
		
		// We use a user service for user processes and BankService for banking processes
		UserDAO ud = new UserDAO();
		BankDAO bd = new BankDAO();
		UserService us = new UserService(ud);
		BankService bs = new BankService(bd);
		BankMenu bm = new BankMenu(bs, us);
		
		bankLogger.info("Starting menu application");
		
		while(true) {
			bm.displayMenu();
		}
		
	}
	
	
}
