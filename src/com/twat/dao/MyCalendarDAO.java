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
import com.twat.dto.MyCalendarVO;

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
	
	
	public ArrayList<MyCalendarVO> getInfo(String MEMBER_ID){
		System.out.println(MEMBER_ID);
		System.out.println(MEMBER_ID);
		System.out.println(MEMBER_ID);
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MyCalendarVO> myCalList = new ArrayList<MyCalendarVO>();
		String sql = "SELECT * FROM MY_CALENDAR WHERE MEMBER_ID = ?";

		try{
			con = getConnection();
			pstmt = con.prepareCall(sql);
			pstmt.setString(1, MEMBER_ID);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				myCalList.add(new MyCalendarVO(rs.getInt(1), rs.getString(2), rs.getTimestamp(3), rs.getString(4), rs.getString(5)));
			}
//			while(rSet.next()){
//				MyCalendarVO myCalInfo = new MyCalendarVO();
//				myCalInfo.setMy_cal_index(rSet.getInt(1));
//				myCalInfo.setMember_id(rSet.getString(2));
//				myCalInfo.setMy_write_time(rSet.getTimestamp(3));
//				myCalInfo.setMy_cal_contents(rSet.getString(4));
//				myCalInfo.setMy_cal_date(rSet.getString(5));
//				
//				arrList.add(myCalInfo);
//			}
		}
		catch (Exception e){
			e.printStackTrace();
		} finally {
			try{
				if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();
			}	catch (SQLException e){
				e.printStackTrace();
			}
		}
		return myCalList;
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
