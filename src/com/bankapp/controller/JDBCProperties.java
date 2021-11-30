package com.bankapp.controller;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JDBCProperties {

	  static String driverClass = null;
	 static String url = null;
	 static String username = null;
	 static String password = null;
	 
	 static Connection con;
	 
	 static void loadProperties() {
		
		try {
			
			FileInputStream fis=new FileInputStream("resources/database.properties");
			
			Properties prop=new Properties();
			prop.load(fis);
			driverClass=prop.getProperty("MySQLJdbc.driver");
			url=prop.getProperty("MySQLJdbc.url");
			username=prop.getProperty("MySQLJdbc.user");
			password=prop.getProperty("MySQLJdbc.pass");
				
			prop.clear();
			fis.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	 
	 public static Connection getConnection() {
		 try {
			 loadProperties();
			 Class.forName(driverClass);
			 con=DriverManager.getConnection(url,username,password);
			 
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 
		return con;	 
	 }
	 
	 
	 
	 

//	public static void main(String[] args) {
//		loadProperties();
//		System.out.println(JDBCProperties.driverClass);
//		System.out.println(JDBCProperties.url);
//		System.out.println(JDBCProperties.username);
//		System.out.println(JDBCProperties.password);
//		
//	}
//	
	
}
