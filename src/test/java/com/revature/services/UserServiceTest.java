package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserDAO;

public class UserServiceTest {
	
	
	
	private UserService us;
	private UserDAO ud; 
	
	
	@BeforeEach
	public void setupUserService() {
		this.ud = mock(UserDAO.class);
		this.us = new UserService(this.ud);
	}
	
	@Test
	public void testUserLogin() throws UserNotFoundException {
		User expectedUser = new User();
		expectedUser.setUserId(1);
		expectedUser.setUsername("username");
		expectedUser.setPassword("password");

		when(ud.findOneUser("username", "password")).thenReturn(expectedUser);
		assertEquals(expectedUser, this.us.login("username", "password"));
		verify(ud).findOneUser("username", "password");
	}
	
}
