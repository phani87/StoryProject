package com.cnsi.story.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDB {

	public String URL;
	public String JDBCDRIVER;
	public String USERNAME;
	public String PASSWORD;

	public ConnectionDB() {
		getPropertyValues();
	}

	public String getPropertyValues() {
		String result = "";
		Properties prop = new Properties();
		String propFileName = "dbConfig.properties";

		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(propFileName);

		if (inputStream != null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				throw new FileNotFoundException("property file '"
						+ propFileName + "' not found in the classpath");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// get the property value and print it out
		URL = prop.getProperty("URL");
		USERNAME = prop.getProperty("USERNAME");
		PASSWORD = prop.getProperty("PASSWORD");
		JDBCDRIVER = prop.getProperty("JDBCDRIVER");
		return result;
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(JDBCDRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("---------Connection Open-------");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;

	}
	
	public void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeConnection(Connection connection, ResultSet resultSet, PreparedStatement preparedStatement){
		try {
			resultSet.close();
			preparedStatement.close();
			connection.close();
			System.out.println("---------Connection Closed-------");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeConnection(Connection connection, PreparedStatement preparedStatement){
		try {
			preparedStatement.close();
			connection.close();
			System.out.println("---------Connection Closed-------");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ConnectionDB db = new ConnectionDB();
		db.getConnection();
	}
}
