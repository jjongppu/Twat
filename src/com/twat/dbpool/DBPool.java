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
<<<<<<< HEAD
		DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/twhat");
=======
		DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/aclass0201");
//		DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/twhat");
>>>>>>> fcce9538ca8559abfbda06ee8b48a031f3d64cd0

		return (Connection) ds.getConnection();
	}
	

}
