package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.repositories.UserDAO;

public class UserServiceTest {
	
	
	
	private UserService userService;
	private UserDAO ud; 
	
	
	@BeforeEach
	public void setupUserService() {
		this.ud = mock(UserDAO.class);
		this.userService = new UserService(this.ud);
	}
	
	@Test
	public void testUserLogin() {
		assertEquals(4, 4);
	}
	
	@Test
	public void failingTest() {
		fail();
	}
}
