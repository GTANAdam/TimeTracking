package com.timetracking.user.dao;

import com.timetracking.dao.MainDAO;
import com.timetracking.user.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class UserDAO extends MainDAO implements IUserDAO 
{	
	final static Logger logger = Logger.getLogger(MainDAO.class);
	private static final String table = "users";

	public List<User> getUsers()
	{
		String statement = "SELECT * FROM " + table;
		
		// using try-with-resources to avoid closing resources (boiler plate code)
		List<User> users = new ArrayList<>();
		
		// Step 1: Establishing a Connection
		// Step 2: Create a statement using connection object
		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(statement);) 
		{
			logger.debug(preparedStatement);
			
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) 
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String role = rs.getString("role");
				
				users.add(new User(id, name, email, password, role));
			}
		}
		catch (SQLException e) 
		{
			printSQLException(e);
		}
		
		return users;
	}
	
	public List<User> getUsers(int start, int total)
	{
		String statement = "SELECT * FROM " + table + " LIMIT ?, ?";
		int cstart = start * total - total;
		
		// using try-with-resources to avoid closing resources (boiler plate code)
		List<User> users = new ArrayList<>();
		
		// Step 1: Establishing a Connection
		// Step 2: Create a statement using connection object
		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(statement);) 
		{
			preparedStatement.setInt(1, cstart);
			preparedStatement.setInt(2, total);
			logger.debug(preparedStatement);
			
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) 
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String role = rs.getString("role");
				
				users.add(new User(id, name, email, password, role));
			}
		}
		catch (SQLException e) 
		{
			printSQLException(e);
		}
		
		return users;
	}

	public int getNumberOfRows() 
	{
		String statement = "SELECT COUNT(id) FROM " + table;
		int numOfRows = 0;

		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(statement);) 
		{
			logger.debug(preparedStatement);
			
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) 
			{
				numOfRows = rs.getInt(1);
			}
			
		}
		catch (SQLException e) 
		{
			printSQLException(e);
		}
        
        return numOfRows;
    }

	public int add(User user) 
	{
		String statement = "INSERT INTO " + table + " (name, email, password, role) VALUES (?, ?, ?, ?)";
		int result = 0;
		
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(statement)) 
		{
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getRole());
			
			logger.debug(preparedStatement);
			result = preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			printSQLException(e);
		}
		
		return result;
	}
	
	public boolean update(User user)
	{
		String statement = "UPDATE " + table + " SET name = ?, email = ?, password = ?, role = ? WHERE id = ?";
		
		boolean rowUpdated = false;
		try (Connection connection = getConnection(); PreparedStatement preparedstatement = connection.prepareStatement(statement);) 
		{
			preparedstatement.setString(1, user.getName());
			preparedstatement.setString(2, user.getEmail());
			preparedstatement.setString(3, user.getPassword());
			preparedstatement.setString(4, user.getRole());
			
			preparedstatement.setInt(5, user.getId());

			logger.debug(preparedstatement);
			rowUpdated = preparedstatement.executeUpdate() > 0;
		}
		catch (SQLException e) 
		{
			printSQLException(e);
		}
		
		return rowUpdated;
	}

	public boolean delete(int id)
	{
		String statementString = "DELETE FROM " + table + " WHERE id = ?";
		
		boolean rowDeleted = false;
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(statementString);) 
		{
			statement.setInt(1, id);
			
			logger.debug(statement);
			rowDeleted = statement.executeUpdate() > 0;
		}
		catch (SQLException e) 
		{
			printSQLException(e);
		}
		
		return rowDeleted;
	}

	public User getById(int id) 
	{
		String statement = "SELECT name, email, password, role FROM " + table + " WHERE id = ?";
		User user = null;
		
		// Step 1: Establishing a Connection
		// Step 2: Create a statement using connection object
		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(statement);)
		{
			preparedStatement.setInt(1, id);
			logger.debug(preparedStatement);
			
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) 
			{
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String role = rs.getString("role");
				
				user = new User(id, name, email, password, role);
			}
		} 
		catch (SQLException e) 
		{
			printSQLException(e);
		}
		
		return user;
	}
}
