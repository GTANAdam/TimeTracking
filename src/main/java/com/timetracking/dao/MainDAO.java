package com.timetracking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class MainDAO
{	
	final static Logger logger = Logger.getLogger(MainDAO.class);
	
	final private static String jdbcHost = "localhost:3306";
	final private static String jdbcDatabase = "time_tracking_db";
	final private static String jdbcUsername = "root";
	final private static String jdbcPassword = "root";
	
	final private static String jdbcURL = "jdbc:mysql://" + jdbcHost + "/" + jdbcDatabase +"?useSSL=false";
	
	protected Connection getConnection() 
	{
		Connection connection = null;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return connection;
	}
	
	protected void printSQLException(SQLException ex)
	{
		for (Throwable e : ex) 
		{
			if (e instanceof SQLException) 
			{
				e.printStackTrace(System.err);
				logger.error("SQLState: " + ((SQLException) e).getSQLState());
				logger.error("Error Code: " + ((SQLException) e).getErrorCode());
				logger.error("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				
				while (t != null) 
				{
					logger.error("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}