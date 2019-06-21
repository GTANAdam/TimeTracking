package com.timetracking.auth.dao;

import com.timetracking.dao.MainDAO;
import com.timetracking.user.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class AuthDAO extends MainDAO implements IAuthDAO 
{	
	final static Logger logger = Logger.getLogger(MainDAO.class);
	private static final String table = "users";
	
	public User getAccount(String emailStr, String passwordStr) 
	{
		String statement = "SELECT * FROM " + table + " WHERE email = ? AND password = ?";
		User user = null;
		
		// Step 1: Establishing a Connection
		// Step 2: Create a statement using connection object
		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(statement);)
		{
			preparedStatement.setString(1, emailStr);
			preparedStatement.setString(2, passwordStr);
			
			// Log statement
			logger.debug(preparedStatement);
			
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) 
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String role = rs.getString("role");
				
				user = new User(id, name, emailStr, passwordStr, role);
			}
		}
		catch (SQLException e) 
		{
			printSQLException(e);
		}
		
		return user;
	}
 
}
