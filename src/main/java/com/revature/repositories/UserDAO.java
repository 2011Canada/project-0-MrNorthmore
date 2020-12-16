package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.EmployeeNotFoundException;
import com.revature.exceptions.InternalErrorException;
import com.revature.exceptions.UnableToCreateUserException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class UserDAO implements IUserDAO {

	private ConnectionFactory cf = ConnectionFactory.getConnectionFactory();

	public User createNewUser(String username, String password)
			throws UserNotFoundException, InternalErrorException, UnableToCreateUserException {
		Connection conn = this.cf.getConnection();
		try {

			// Create our SQL string with ? placeholder values
			String sql = "insert into \"users\" (\"username\", \"password\")" + "	values (?, ?)"
					+ "	returning \"user_id\", \"username\", \"password\";";

			// Create a prepared statement to fill with user values
			PreparedStatement insertUser = conn.prepareStatement(sql);

			// Insert user name and password values into the statement
			insertUser.setString(1, username);
			insertUser.setString(2, password);

			ResultSet res = insertUser.executeQuery();

			int newUserId;

			if (res.next()) {
				User user = new User();
				user.setUserId(res.getInt("user_id"));
				user.setUsername(username);
				user.setPassword(password);
				return user;
			} else {
				throw new UnableToCreateUserException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new InternalErrorException();
		}
	}

	public User findOneUser(String username, String password) throws UserNotFoundException {
		Connection conn = this.cf.getConnection();
		User user = null;
		try {

			String sql = "select * from users where \"username\" = ? and \"password\" = ?;";
			PreparedStatement findUser = conn.prepareStatement(sql);

			findUser.setString(1, username);
			findUser.setString(2, password);

			ResultSet res = findUser.executeQuery();

			if (res.next()) {
				user = new User();
				user.setUserId(res.getInt("user_id"));
				user.setUsername(username);
				user.setPassword(password);
			}
		} catch (SQLException e) {
			throw new UserNotFoundException();
		}
		return user;
	}

	public List<User> findAll() {

		Connection conn = this.cf.getConnection();
		List<User> allUsers = new ArrayList<User>();

		try {
			String sql = "select * from users";
			Statement s = conn.createStatement();
			ResultSet res = s.executeQuery(sql);

			while (res.next()) {
				User user = new User();
				user.setUsername(res.getString("username"));
				user.setPassword(res.getString("password"));
				allUsers.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return allUsers;
	}

	public Employee findEmployee(String username, String password) throws EmployeeNotFoundException {
		Connection conn = this.cf.getConnection();
		Employee em = null;
		
		try {

			// Create our SQL string with ? placeholder values
			String sql = "select u.user_id, u.username, u.\"password\", e.employee_id from users u "
					+ "	inner join employees e on u.user_id = e.user_id and u.username = ? and u.\"password\" = ?;";

			// Create a prepared statement to fill with user values
			PreparedStatement insertUser = conn.prepareStatement(sql);

			// Insert user name and password values into the statement
			insertUser.setString(1, username);
			insertUser.setString(2, password);

			ResultSet res = insertUser.executeQuery();

			if (res.next()) {
				em = new Employee();
				em.setUserId(res.getInt("user_id"));
				em.setUsername(res.getString("username"));
				em.setPassword(res.getString("password"));
				em.setEmployeeNum(res.getInt("employee_id"));
			}

		} catch (SQLException e) {
			throw new EmployeeNotFoundException();
		}

		return em;
	}
	
	public User getOneUserByUsername(String username) {
		Connection conn = this.cf.getConnection();
		User foundUser = null;
		try {
			String sql = "select * from users where \"username\" = ?;";
			PreparedStatement selectUsers = conn.prepareStatement(sql);
			selectUsers.setString(1, username);
			ResultSet res = selectUsers.executeQuery();
			
			if(res.next()) {
				foundUser = new User();
				foundUser.setUsername(res.getString("username"));
				foundUser.setUserId(res.getInt("user_id"));
				foundUser.setPassword(res.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foundUser;
	}
}
