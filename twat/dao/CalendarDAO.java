package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.twat.dto.CalendarVO;
import com.twat.dto.CalgatherVO2;

public class CalendarDAO
{
	Connection con = null;
	PreparedStatement psmt= null;
	ResultSet rs= null;

	
	// CalendarDAO 의 싱글톤 ================================================================================================
	private static CalendarDAO instance = new CalendarDAO();
	
	private CalendarDAO() {	}
	
	public static CalendarDAO getInstance()
	{
		return instance;
	}
	
	// DB연결을 위해 con을 반환하는 메서드 ==================================================================================
	public Connection getConnection() throws Exception
	{
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/aclass0201");      
			      
		return ds.getConnection();
	}

	// 각종 일정 받아오기 ===================================================================================================
	public ArrayList<CalendarVO> getInfo(String groupId)
	{
		ArrayList<CalendarVO> arrList = new ArrayList<CalendarVO>();
		
		try
		{
			con = getConnection();
			String sql = "select * from CALENDAR where CAL_DEPTH=0 and GROUP_ID=?";
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, Integer.parseInt(groupId));
			
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				CalendarVO schedule = new CalendarVO();
				schedule.setCal_num(rs.getInt(1));
				schedule.setCal_date(rs.getString(3));
				schedule.setCal_group(rs.getInt(4));
				schedule.setCal_memo(rs.getString(5));
				schedule.setCal_writer(rs.getString(6));
				schedule.setStat_icon(rs.getString(7));
				schedule.setMember_choice(rs.getString(8));
				schedule.setCal_reference(rs.getInt(9));
				schedule.setCal_depth(rs.getInt(10));
				
				arrList.add(schedule);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs != null)rs.close();
				if(psmt != null)psmt.close();
				if(con != null)con.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		return arrList;
	}	
}
