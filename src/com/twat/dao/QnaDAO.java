package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class QnaDAO {
	Connection con = null;
	PreparedStatement psmt= null;
	ResultSet rs= null;
	
	// MemberDAO 의 싱글톤 -----------------------------------
	private static QnaDAO instance = new QnaDAO();
		
	private QnaDAO(){}
		
	public static QnaDAO getInstance(){
		return instance;
	}
		
	
	// DB연결을 위해 con을 반환하는 메서드 --------------------------------------------
	public Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
	    DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
	         
	    return ds.getConnection();
	}
	
	
	// 메서드들 들어감
//	public int insertQnA(String ) {
//		
//	}
		
}
