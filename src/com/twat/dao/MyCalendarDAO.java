package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

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
	
	public void addMySchedule(int myCalIndex, String member_id, String my_cal_contents, String my_cal_date ) {
		String sql ="";
		try {
			con = getConnection();			
			sql = "insert into my_calendar VALUES(?, ?, CURRENT_TIMESTAMP, ?, ?)";
			String[] dateStr = my_cal_date.split(",");
			
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, myCalIndex);			
			psmt.setString(2, member_id);
			psmt.setString(3, my_cal_contents);
			psmt.setString(4, my_cal_date);
			
			psmt.executeUpdate();
//			psmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(psmt != null)
					psmt.close();
				if(con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	
	
	
}
