package com.twat.dbpool;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class DBPool {
	
	Connection con = null;
	
	private static DBPool instance = new DBPool();

	private DBPool() {
	}

	public static DBPool getInstance() {
		return instance;
	}
	
	
	public Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
//		DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/aclass0201");
		DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/twhat");

		return (Connection) ds.getConnection();
	}
	

}
