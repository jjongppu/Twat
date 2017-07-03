package com.admin.dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class NoticeBoardDAO {
	Connection con = null;

	
	
	// MemberDAO �� �̱��� -----------------------------------
	private static NoticeBoardDAO instance = new NoticeBoardDAO();
	
	private NoticeBoardDAO(){}
	
	public static NoticeBoardDAO getInstance(){
		return instance;
	}
	 
	// -------------------------------------------------------
	
	
	
	// DB������ ���� con�� ��ȯ�ϴ� �޼��� --------------------------------------------
	public Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
	    DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
	         
	    return ds.getConnection();
	}
	
	
	
	
	
	
	
	
	
	

}
