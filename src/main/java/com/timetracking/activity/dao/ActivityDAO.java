package com.timetracking.activity.dao;

import com.timetracking.activity.model.Activity;
import com.timetracking.dao.MainDAO;
import com.timetracking.user.dao.UserDAO;
import com.timetracking.user.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class ActivityDAO extends MainDAO implements IActivityDAO 
{	
	final static Logger logger = Logger.getLogger(MainDAO.class);
	private static final String table = "activities";

	public List<Activity> getActivities(int start, int total)
	{
		String statement = "SELECT * FROM " + table + " LIMIT ?, ?";
		int cstart = start * total - total;

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Activity> activities = new ArrayList<>();
		
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
				Date startDate = rs.getDate("startDate");
				Date endDate = rs.getDate("endDate");
				String status = rs.getString("status");
				int userId = rs.getInt("user_id");
				
				// Get user by id
				User user = new UserDAO().getById(userId);
				
				activities.add(new Activity(id, user, name, startDate, endDate, status));
			}
		}
		catch (SQLException e) 
		{
			printSQLException(e);
		}
		
		return activities;
	}

	public int getNumberOfRows() {
        
		int numOfRows = 0;
	
		String statement = "SELECT COUNT(id) FROM " + table;

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


	public List<Activity> getActivitiesForUser(int userId, int start, int total)
	{
		String statement = "SELECT * FROM " + table + " WHERE user_id = ? LIMIT ?, ? ";
		int cstart = start * total - total;
		
		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Activity> activities = new ArrayList<>();
		
		// Step 1: Establishing a Connection
		// Step 2: Create a statement using connection object
		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(statement);) 
		{
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, cstart);
			preparedStatement.setInt(3, total);
			logger.debug(preparedStatement);
			
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) 
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Date startDate = rs.getDate("startDate");
				Date endDate = rs.getDate("endDate");
				String status = rs.getString("status");
				
				// Get user by id
				User user = new UserDAO().getById(userId);
				
				activities.add(new Activity(id, user, name, startDate, endDate, status));
			}
		}
		catch (SQLException e) 
		{
			printSQLException(e);
		}
		
		return activities;
	}

	public void add(Activity activity) 
	{
		String statement = "INSERT INTO " + table + " (name, startDate, endDate, status, user_id) VALUES (?, ?, ?, ?, ?)";
		
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(statement)) 
		{
			preparedStatement.setString(1, activity.getName());
			preparedStatement.setDate(2, activity.getStartDate());
			preparedStatement.setDate(3, activity.getEndDate());
			preparedStatement.setString(4, activity.getStatus());
			preparedStatement.setInt(5, activity.getUser().getId());
			
			// Log statement
			logger.debug(preparedStatement);
			
			// Execute query
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			printSQLException(e);
		}
	}
	
	public boolean update(Activity activity)
	{
		String statement = "UPDATE " + table + " SET name = ?, startDate = ?, endDate = ?, status = ?, user_id = ? WHERE id = ?";
		
		boolean rowUpdated = false;
		try (Connection connection = getConnection(); PreparedStatement preparedstatement = connection.prepareStatement(statement);) 
		{
			preparedstatement.setString(1, activity.getName());
			preparedstatement.setDate(2, activity.getStartDate());
			preparedstatement.setDate(3, activity.getEndDate());
			preparedstatement.setString(4, activity.getStatus());
			preparedstatement.setInt(5, activity.getUser().getId());
			preparedstatement.setLong(6, activity.getId());

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

	public Activity getById(int id) 
	{
		String statement = "SELECT name, startDate, endDate, status, user_id FROM " + table + " WHERE id = ?";
		Activity activity = null;
		
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
				Date startDate = rs.getDate("startDate");
				Date endDate = rs.getDate("endDate");
				String status = rs.getString("status");
				int userId = rs.getInt("user_id");
				
				// Get user by id
				User user = new UserDAO().getById(userId);
				
				activity = new Activity(id, user, name, startDate, endDate, status);
			}
		}
		catch (SQLException e) 
		{
			printSQLException(e);
		}
		
		return activity;
	}
}
