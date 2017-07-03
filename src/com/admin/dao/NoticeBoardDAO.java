package com.admin.dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class NoticeBoardDAO {
	Connection con = null;

	
	
	// MemberDAO 의 싱글톤 -----------------------------------
	private static NoticeBoardDAO instance = new NoticeBoardDAO();
	
	private NoticeBoardDAO(){}
	
	public static NoticeBoardDAO getInstance(){
		return instance;
	}
	 
	// -------------------------------------------------------
	
	
	
	// DB연결을 위해 con을 반환하는 메서드 --------------------------------------------
	public Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
	    DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
	         
	    return ds.getConnection();
	}
	
	
	
	
	
	
	
	
	
	

}
