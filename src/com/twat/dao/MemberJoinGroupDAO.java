package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.twat.dto.CalgatherVO;

public class MemberJoinGroupDAO
{
	Connection con = null;
	PreparedStatement psmt= null;
	ResultSet rs= null;

	
	// MemberJoinGroupDAO �� �̱��� -----------------------------------
	private static MemberJoinGroupDAO instance = new MemberJoinGroupDAO();
	
	private MemberJoinGroupDAO(){}
	
	public static MemberJoinGroupDAO getInstance()
	{
		return instance;
	}
	
	// DB������ ���� con�� ��ȯ�ϴ� �޼��� --------------------------------------------
	public Connection getConnection() throws Exception
	{
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
	         
		return ds.getConnection();
	}
	
	// GROUP_ID�� ���� MEMBER_ID�� �޾ƿ��� �Լ�, MEMBER_ID�� ArrayList<String>���� ��ȯ
	public ArrayList<String> getMemberId(String GROUP_ID)
	{
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		ArrayList<String> arrList = new ArrayList<String>();
		String sql = "select MEMBER_ID from MEMBER_JOIN_GROUP where GROUP_ID=?";
		
		try
		{
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, GROUP_ID);
			
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next())
			{
				arrList.add(resultSet.getString(1));
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
				if(resultSet != null)resultSet.close();
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		return arrList;
	}
	
	// MEMBER_ID�� ���� GROUP_ID�� �޾ƿ��� �Լ�, GROUP_ID�� ArrayList<String>���� ��ȯ
	public ArrayList<String> getGroupId(String MEMBER_ID)
	{
		ArrayList<String> arrList = new ArrayList<String>();
		String sql = "select GROUP_ID from MEMBER_JOIN_GROUP where MEMBER_ID=?";
		
		try
		{
			con = getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, MEMBER_ID);
			
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				arrList.add(rs.getString(1));
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
