package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.jdbc.pool.DataSource;

public class MyCalendarDAO {
	
	Connection con = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	Statement stmt;
	
	private static MyCalendarDAO instance = new MyCalendarDAO();
	
	private MyCalendarDAO() {}
	
	public static MyCalendarDAO getInstance(){
		return instance;
	}
	
	public Connection getConnection() throws Exception{
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");
		
		return ds.getConnection();
	}
	
	
}
