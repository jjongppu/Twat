package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public void addGroupCal(int cal_num, String cal_date, int group_id, String cal_memo, String cal_writer ) {
		String sql ="";
		try {
			con = getConnection();			
			sql = "insert into calendar VALUES(?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, cal_num);			
			psmt.setString(2, cal_date);
			psmt.setInt(3, group_id);
			psmt.setString(4, cal_memo);
			psmt.setString(5, cal_writer);
			psmt.setString(6, "");
			psmt.setString(7, "");
			psmt.setInt(8, cal_num);
			psmt.setInt(9, 0);
			
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
