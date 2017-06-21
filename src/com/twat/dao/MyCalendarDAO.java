package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import com.twat.dto.CalendarVO;
import com.twat.dto.CalgatherVO;

public class MyCalendarDAO{
	Connection con = null;
	PreparedStatement psmt= null;
	ResultSet rs= null;
	Statement stmt;
	
	private static MyCalendarDAO instance = new MyCalendarDAO();
	
	private MyCalendarDAO() {	}
	
	public static MyCalendarDAO getInstance(){
		return instance;
	}
	
	public Connection getConnection() throws Exception{
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");   
			      
		return ds.getConnection();
	}

	public void addMySchedule( String member_id, String my_cal_contents, String my_cal_date ) {
		PreparedStatement psmt2= null;
		String sql ="";
		try {
			con = getConnection();			
			sql = "INSERT INTO MY_CALENDAR VALUES(?, ?, CURRENT_TIMESTAMP, ?, ?)";
			psmt2 = con.prepareStatement(sql);			
			psmt2.setInt(1, getMaxNum());
			System.out.println(getMaxNum());
			psmt2.setString(2, member_id);
			psmt2.setString(3, my_cal_contents);
			psmt2.setString(4, my_cal_date);
			
			psmt2.executeUpdate();
//			psmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(psmt2 != null)
					psmt2.close();
				if(con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	
	
	
	public int getMaxNum() {
		int result = -1;
		int cal_num = 0;
		
		String selectSql = "SELECT * FROM `my_calendar` ORDER BY MY_CAL_INDEX DESC LIMIT 1";
		
		try {
			con = getConnection();
			psmt = con.prepareStatement(selectSql);
			
			rs = psmt.executeQuery();
			while(rs.next()){
				cal_num = rs.getInt("MY_CAL_INDEX");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	        try {
	        	if(rs != null) rs.close();
		        if(psmt != null) psmt.close();
		        if(con != null) con.close();
	        } catch (SQLException e) {
		        e.printStackTrace();
	        }
	        return cal_num + 1;
		}    
		
	}

	
}
