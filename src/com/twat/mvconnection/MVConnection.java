package com.twat.mvconnection;

import java.sql.SQLException;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class MVConnection {

	 private Connection conn; // 角力 目池记
     
     private java.util.List<Statement> statementList; // statement甫 历厘
     
     public MVConnection(Connection conn) {
         this.conn = conn;
         statementList = new java.util.ArrayList<Statement>();
     }
     
     public void closeAll() {
         for (int i = 0 ; i < statementList.size() ; i++) {
             Statement stmt = (Statement)statementList.get(i);
             try {
                 stmt.close();
             } catch(SQLException ex) {}
         }
     }
     
     public void close() throws SQLException {
         this.closeAll();            conn.close();
     }
     
     public Statement createStatement() throws SQLException {
         Statement stmt = (Statement) conn.createStatement();
         statementList.add(stmt);
         return stmt;
     }
     
     public CallableStatement prepareCall(String sql) throws SQLException {
         CallableStatement cstmt = (CallableStatement) conn.prepareCall(sql);
         statementList.add(cstmt);
         return cstmt;
     }
     
     public PreparedStatement prepareStatement(String sql) throws SQLException {
         PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
         statementList.add(pstmt);
         return pstmt;
     }
     
     public boolean getAutoCommit() throws SQLException {
         return conn.getAutoCommit();
         
     }

 
     
     
     
     


	
}
