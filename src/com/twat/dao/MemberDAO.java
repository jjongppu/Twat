package com.twat.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.twat.dto.MemberVO;

	

public class MemberDAO {
	Connection con = null;
	PreparedStatement psmt= null;
	ResultSet rs= null;
	
	
	// MemberDAO ï¿½ï¿½ ï¿½Ì±ï¿½ï¿½ï¿½ -----------------------------------
	private static MemberDAO instance = new MemberDAO();
	
	private MemberDAO(){}
	
	public static MemberDAO getInstance(){
		return instance;
	}
	 
	// -------------------------------------------------------
	
	
	
	   // DBï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ conï¿½ï¿½ ï¿½ï¿½È¯ï¿½Ï´ï¿½ ï¿½Þ¼ï¿½ï¿½ï¿½ --------------------------------------------
	   public Connection getConnection() throws Exception {
	         Context initCtx = new InitialContext();
	         DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
	         
	      return ds.getConnection();
	   }

	
	
	  // ï¿½ï¿½ï¿½Ìµï¿½ Ã£ï¿½ï¿½ ï¿½Þ¼ï¿½ï¿½ï¿½ (ï¿½Ì¸ï¿½,ï¿½ï¿½È­ï¿½ï¿½È£ï¿½ï¿½ Ã£ï¿½ï¿½)-----ï¿½Â¿ï¿½-------------------------
	   public String searchID(String MEMBER_NAME, String MEMBER_PHONE) {
	      
	      String selectSql = "select MEMBER_ID from MEMBER where MEMBER_NAME=? and MEMBER_PHONE=?";
	      String getID = ""; 
	      
	      try {
	         con = getConnection();
	         psmt = con.prepareStatement(selectSql);
	         psmt.setString(1, MEMBER_NAME);
	         psmt.setString(2, MEMBER_PHONE);
	         
	         rs = psmt.executeQuery();
	         
	         while(rs.next()) {
	            getID = rs.getString(1);
	         }
	         
	      } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      } finally {
	         try {
	            if(rs != null)   rs.close();
	            if(psmt != null) psmt.close();
	            if(con != null) con.close();
	         } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         }
	      
	   }
	      return getID;
	   }
	   
	   
	   
// ï¿½ï¿½Ð¹ï¿½È£ Ã£ï¿½ï¿½ ï¿½Þ¼ï¿½ï¿½ï¿½ ---------ï¿½Â¿ï¿½-------------------------------
	   public String searchPW(String MEMBER_ID, String MEMBER_NAME, String MEMBER_PHONE) {
		   String selectSql = "select MEMBER_PW from MEMBER where MEMBER_ID=? and MEMBER_NAME=? and MEMBER_PHONE=?";
		   String getPW = "";
		   
		   try {
			con = getConnection();
			psmt = con.prepareStatement(selectSql);
			psmt.setString(1, MEMBER_ID);
			psmt.setString(2, MEMBER_NAME);
			psmt.setString(3, MEMBER_PHONE);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				getPW = rs.getString(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	         try {
		            if(rs != null)   rs.close();
		            if(psmt != null) psmt.close();
		            if(con != null) con.close();
		         } catch (SQLException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		         }
		      
		   }
		   return getPW;
	   }	   
	   

		// È¸ï¿½ï¿½ ï¿½Î±ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½Þ¼ï¿½ï¿½ï¿½ ----------------------------------
		public int loginMember(String MEMBER_ID, String MEMBER_PW) {
			int result = -1;
			
			long currentTime = System.currentTimeMillis();
			   
			System.out.println(currentTime);
			
			String selectSql = "select OUT_TIME from MEMBER where MEMBER_ID = ? and MEMBER_PW = ?";
			
			
			try {
				con = getConnection();
				psmt = con.prepareStatement(selectSql);
				psmt.setString(1, MEMBER_ID);
				psmt.setString(2, MEMBER_PW);
//				System.out.println("!");
				rs = psmt.executeQuery();
				
//				if(rs.next()) {
//					// out_timeï¿½ï¿½ 0ï¿½Ì¸ï¿½ ï¿½Î±ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ resultï¿½ï¿½ 1ï¿½Ö¾ï¿½ï¿½ï¿½
//					if(rs.getInt(1) == 0) {
//						// ï¿½Î±ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
//						result = 1;
//					} else {
//						result = rs.getInt(1);
//						System.out.println(result);
//					}
//				}
				
				if(rs.next()) {
					// out_timeï¿½ï¿½ 0ï¿½Ì¸ï¿½ ï¿½Î±ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ resultï¿½ï¿½ 1ï¿½Ö¾ï¿½ï¿½ï¿½
					if(rs.getLong(1) == 0) {
						// ï¿½Î±ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
						result = 1;
					} else if(rs.getLong(1) > currentTime) { // Å»ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ È¸ï¿½ï¿½
						result = -1;
//						System.out.println(result);
					} else if(rs.getLong(1) < currentTime) { // Å»ï¿½ï¿½ï¿½ È¸ï¿½ï¿½
						result = -2;
					} else { // ï¿½ï¿½ï¿½Ìµï¿½, ï¿½ï¿½ï¿½ Æ²ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
						result = 0;
					}
				}
			} catch (Exception e) {
				System.out.print("ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
					try {
						if(rs != null)rs.close();
						if(psmt != null) psmt.close();
						if(con != null) con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
			return result;
		}
	   
	   
	   
	// ï¿½Î±ï¿½ï¿½ï¿½ ï¿½Ñ»ï¿½ï¿½ï¿½ï¿½ ï¿½×·ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Ð±â¸®
			public ArrayList<Integer> getMyGroupList(String MEMBER_ID) {
				ArrayList<Integer> glList = new ArrayList<Integer>();
				
				
				try {
					con = getConnection();
					
					// ï¿½ï¿½ï¿½Ìµï¿½ï¿½ ï¿½×·ï¿½ï¿½ï¿½ ï¿½Ò·ï¿½ï¿½ï¿½ï¿½ï¿½
					String sql = "select GROUP_ID from MEMBER_JOIN_GROUP where MEMBER_ID =?";
					
					psmt = con.prepareStatement(sql);
					psmt.setString(1, MEMBER_ID);
					
					rs = psmt.executeQuery();
					while(rs.next()) {
						glList.add(rs.getInt("GROUP_ID"));
					}
						
						
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
						try {
							if(rs != null)	rs.close();
							if(psmt != null) psmt.close();
							if(con != null) con.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}
				return glList;
			}


	   
	   
// È¸ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½Þ¼ï¿½ï¿½ï¿½ ------ï¿½Â¿ï¿½----------------------------

	   public int signUpMember(String MEMBER_ID, String MEMBER_PW, String MEMBER_NAME, String MEMBER_PHONE, 
			   String MEMBER_GENDER, String MEMBER_BIRTH, int OUT_TIME, String MEMBER_QUESTION, String MEMBER_ANSWER) {

	      
	      int result = 0;
	      int signUp = 0;
	      
//	      String insertSql = "insert into MEMBER values(?,?,?,?,?,?,?,?,?,?)";

	      String insertSql = "insert into MEMBER values(?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,?,?)";

	      
	      try {
	         con = getConnection();
	         psmt = con.prepareStatement(insertSql);
	      
	         psmt.setString(1, MEMBER_ID);
	         psmt.setString(2, MEMBER_PW);
	         psmt.setString(3, MEMBER_NAME);
	         psmt.setString(4, MEMBER_PHONE);
	         psmt.setString(5, "img/member/basis_photo.png");
	         psmt.setString(6, MEMBER_GENDER);
	         psmt.setString(7, MEMBER_BIRTH);
	         psmt.setString(8, null);
	         psmt.setString(9, null);
//	         psmt.setTimestamp(10, null);
	         psmt.setInt(10, OUT_TIME);

	         psmt.setString(11, MEMBER_QUESTION);
	         psmt.setString(12, MEMBER_ANSWER);

	         
	         result = psmt.executeUpdate();
//	         System.out.println(result);
//	         rs = psmt.executeQuery();
//	         

	      } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      } finally {
	         try {
	            if(rs != null)   rs.close();
	            if(psmt != null) psmt.close();
	            if(con != null) con.close();
	         } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         }
//	         System.out.println(result);
	         return result;
	      }    

	   }	   
	   

	   
	   
// È¸ï¿½ï¿½ï¿½ï¿½ï¿½Ô½ï¿½ ï¿½ï¿½ï¿½Ìµï¿½ ï¿½Ç½Ã°ï¿½ ï¿½Ë»ï¿½...---------ï¿½Â¿ï¿½------------------------------
	   public int checkID(String idCheck) {
		   int result = -1;
		   
		   String sql = "select MEMBER_ID from MEMBER where MEMBER_ID=?";
		   
		   try {
			con = getConnection();
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, idCheck);
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				result = 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	         try {
		            if(rs != null) rs.close();
		            if(psmt != null) psmt.close();
		            if(con != null) con.close();
		         } catch (SQLException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		         }
		   }
	       return result;
		  
	   }	   
	   
	   
	   

	
	// Ä£ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Ì¾Æ¿ï¿½ï¿½ï¿½Í¾ï¿½ï¿½Ï´ï¿½ ï¿½Þ¼ï¿½ï¿½ï¿½ -----ï¿½ï¿½ï¿½ï¿½ ---------------------
	public ArrayList printFriendList(String MEMBER_ID, String MEMBER_NAME, String MEMBER_BIRTH, String MEMBER_PHONE, String MEMBER_IMG){
		 

		String sql = "SELECT MEMBER_IMG, MEMBER_NAME, MEMBER_BIRTH, MEMBER_PHONE, FRIENDS_LIST FROM MEMBER WHERE MEMBER_ID = 'asdfasdf'";
		//ï¿½Î±ï¿½ï¿½Î¼ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Ìµï¿½ ï¿½Þ¾Æ¿Í¼ï¿½ ï¿½Ö±ï¿½.
		ArrayList arList = new ArrayList();
		
		HttpSession session = null;
	    session.getAttribute(MEMBER_ID);
		
		try {
			con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				// Ä£ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Æ®ï¿½ï¿½ ï¿½É°ï¿½ï¿½ï¿½ strï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½Ö°ï¿½
				String[] str = rs.getString(9).split(",");
				System.out.println(str);
				
				// strï¿½ï¿½ï¿½ï¿½ bï¿½ï¿½ ï¿½Ö´ï¿½ï¿½ï¿½ ï¿½Ë¾Æºï¿½ï¿½ï¿½
				for(int i=0; i<arList.size(); i++){
					// ï¿½ï¿½ï¿½ï¿½ bï¿½ï¿½ ï¿½Ö´Ù¸ï¿½ memberVOï¿½ï¿½Ã¼ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½î¼­ rsï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ arListï¿½ï¿½ add
					if(str[i] == MEMBER_ID) {	
						MemberVO mdo = new MemberVO();
					arList.add(mdo.getMEMBER_NAME());					
					
				}else{
					System.out.println("Ä£ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Ï´ï¿½.");
				}	
			}								
		}
			
			rs.close();
			stmt.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return arList;	
	}
	
	   // Ä£ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Í¼ï¿½ Ä£ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Ñ°ï¿½ï¿½Ö±ï¿½ ï¿½ï¿½ï¿½ï¿½ver....
	   public ArrayList<MemberVO> getFriendList(String MEMBER_ID){
	       
	      String myFrinds ="";
	      String findMyFriendsListSql = "SELECT FRIENDS_LIST FROM MEMBER WHERE MEMBER_ID=?";
	      String getMyFriendsSql = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_PHONE, MEMBER_IMG, MEMBER_GENDER, MEMBER_BIRTH FROM MEMBER WHERE MEMBER_ID=?";
	      ArrayList<MemberVO> myfriendsList = new ArrayList<MemberVO>();
	      
	      try {
	         con = getConnection();
	         psmt = con.prepareStatement(findMyFriendsListSql);
	         psmt.setString(1, MEMBER_ID);
	         rs = psmt.executeQuery();
	         
	         if(rs.next()){
	            if(rs.getString("FRIENDS_LIST") != null){
	               
	               myFrinds = rs.getString("FRIENDS_LIST");
	               
	               // Ä£ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Û¼ï¿½ï¿½Ï´Â°ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ dbï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Ç·ï¿½,,,ï¿½Ý¾Æ¹ï¿½ï¿½ï¿½.,,
//	               try {
//	                  if(rs != null)
//	                  if(psmt != null) psmt.close();
//	                  if(con != null) con.close();
//	               } catch (SQLException e) {
//	                  e.printStackTrace();
//	               }
	               //Ä£ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Ã·ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½..
	               String[] str = myFrinds.split(",");
	               for(int i = 1; i < str.length; i++){
	                  getMyFriendsSql+= "or MEMBER_ID=?";
	               }
//	               con = getConnection();
	               psmt = con.prepareStatement(getMyFriendsSql);
	               for(int i = 0; i < str.length; i++){
	                  psmt.setString(i+1, str[i]);
	               }
	               rs = psmt.executeQuery();
	               
	               while(rs.next()){
	                  MemberVO mv = new MemberVO();
	                  mv.setMEMBER_ID(rs.getString("MEMBER_ID"));
	                  mv.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
	                  mv.setMEMBER_PHONE(rs.getString("MEMBER_PHONE"));
	                  mv.setMEMBER_IMG(rs.getString("MEMBER_IMG"));
	                  mv.setMEMBER_GENDER(rs.getString("MEMBER_GENDER"));
	                  mv.setMEMBER_BIRTH(rs.getString("MEMBER_BIRTH"));
	                  myfriendsList.add(mv);
	               }
	               
	            }
	         }
	         
	      } catch (Exception e) {
	      } finally {
	         try {
	            if(rs != null)
	            if(psmt != null) psmt.close();
	            if(con != null) con.close();
	         } catch (SQLException e) {
	         }
	   }
	      return myfriendsList;   
	   }
	   
	 //È¸ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Í¾ï¿½ï¿½ï¿½---------------ï¿½ï¿½ï¿½ï¿½--------------------------------
	   public ArrayList<MemberVO> myInfo(String MEMBER_ID){
	      String sql = "select MEMBER_IMG, MEMBER_NAME, MEMBER_PHONE, MEMBER_BIRTH from MEMBER where MEMBER_ID = ? ";
	      
	      ArrayList<MemberVO> arList = new ArrayList<MemberVO>(); 
	      
	      try {
	         con = getConnection();
	          psmt = con.prepareStatement(sql);
	            psmt.setString(1, MEMBER_ID);
	            rs = psmt.executeQuery();
	            
	            //ï¿½Ì·ï¿½ï¿½ï¿½ï¿½Ï¸ï¿½ï¿½ï¿½ rs.getstring(1)ï¿½ï¿½ ï¿½Ì¹ï¿½ï¿½ï¿½ 2ï¿½ï¿½ ï¿½Ì¸ï¿½ 3ï¿½ï¿½ ï¿½ï¿½È­ï¿½ï¿½È£ 4ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Â´ï¿½.
	            while(rs.next()){
	               MemberVO mvo = new MemberVO();
	               mvo.setMEMBER_IMG(rs.getString("MEMBER_IMG"));
	               mvo.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
	               mvo.setMEMBER_PHONE(rs.getString("MEMBER_PHONE"));
	               mvo.setMEMBER_BIRTH(rs.getString("MEMBER_BIRTH"));
	                 arList.add(mvo);
	            }
	            //arListï¿½ï¿½ ï¿½Ì¹ï¿½ï¿½ï¿½ ï¿½Ì¸ï¿½ ï¿½ï¿½ï¿½ï¿½È£ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Â´ï¿½.
	         
	      } catch (Exception e) {
	      }finally {
	            try {
	                  if(rs != null)
	                  if(psmt != null) psmt.close();
	                  if(con != null) con.close();
	               } catch (SQLException e) {
	               }
	         }
	      return arList;
	   }
	   
	   
//////////////////////////////////////////////////////////////////////////////////////////////	   
	   public ArrayList<String> test(){
		      ArrayList<String> str = new ArrayList<String>(); 
		      String sql = "select * from test";
		      int a=0;
		      
		      try {
		         a=4;
		            con = getConnection();
		            a=5;
		             psmt = con.prepareStatement(sql);
		             a=1;  
		             rs = psmt.executeQuery();
		               a=2;    
		               while(rs.next()){
		                  str.add(rs.getString(1));
		                  str.add(rs.getString(2));
		               }
		               a=3;
		         } catch (Exception e) {
		            e.printStackTrace();
		         }finally {
		               try {
		                     if(rs != null)
		                     if(psmt != null) psmt.close();
		                     if(con != null) con.close();
		                  } catch (SQLException e) {
		                     e.printStackTrace();
		                  }
		            }
		      return str;
		   }

// È¸ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½è¿­ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ È¸ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½È¯ï¿½Ï´ï¿½ ï¿½Ô¼ï¿½, ï¿½è¿­ï¿½ï¿½ ï¿½ï¿½È¯ï¿½Ï¸ï¿½ ï¿½ï¿½ï¿½Â´ï¿½ ï¿½ï¿½ï¿½ï¿½+ï¿½Ì¸ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
	   public ArrayList<MemberVO> getMemberBirth(ArrayList<String> memberList)
	   {
		   ArrayList<MemberVO> arrList = new ArrayList<MemberVO>();
		   String sql = "select MEMBER_ID, MEMBER_NAME, MEMBER_BIRTH from MEMBER";
		   
		   try
		   {
			   con = getConnection();
			   psmt = con.prepareStatement(sql);
			   rs = psmt.executeQuery();
			   
			   while(rs.next())
			   {
				   for(int i = 0; i < memberList.size(); i++)
				   {
					   // memberListï¿½ï¿½ IDï¿½ï¿½ member_idï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Ù¸ï¿½ member ï¿½ï¿½Ã¼ ï¿½ï¿½ï¿½ï¿½ï¿½Ø¼ï¿½ arrListï¿½ï¿½ ï¿½ß°ï¿½
					   if(rs.getString(1).equals(memberList.get(i)))
					   {
						   MemberVO member = new MemberVO();
						   
						   member.setMEMBER_ID(rs.getString(1));
						   member.setMEMBER_NAME(rs.getString(2));
						   member.setMEMBER_BIRTH(rs.getString(3));
						   
						   arrList.add(member);
					   }
				   }
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
				   if(rs != null) rs.close();
				   if(psmt != null) psmt.close();
				   if(con != null) con.close();
			   }
			   catch (SQLException e)
			   {
				   e.printStackTrace();
			   }
		   }
		   
		   return arrList;
	   }

///////////////////////////////////////////////////////////////////ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½Ð¹ï¿½È£ ï¿½ï¿½ï¿½ï¿½.
	   
	   public int changePw(String MEMBER_ID, String nowpwd, String chpwd, String chkpwd){
		   
		   
		   
		   String chkpw = "select MEMBER_PW from MEMBER where MEMBER_ID = ?";
		   // ?ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Þ¾Æ¿ï¿½ ï¿½ï¿½ï¿½Ìµï¿½ ï¿½Ö°ï¿½ ï¿½ï¿½Ð¹ï¿½È£ï¿½ï¿½ ï¿½Þ¾Æ¿Â´ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Þ¾Æ¿ï¿½ ï¿½ï¿½Ð¹ï¿½È£ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Ð¹ï¿½È£ ï¿½Ô·Â¶ï¿½ï¿½Ì¶ï¿½ ï¿½ï¿½ï¿½Ø¼ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Î¹ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Ñ¾î°¡ï¿½ï¿½ ï¿½ï¿½Ð¹ï¿½È£ï¿½ï¿½ ï¿½Ù²ï¿½ï¿½Ø´ï¿½.
		   String changepwd = "update MEMBER set MEMBER_PW = ? where MEMBER_ID = ?";
		   //1ï¿½ï¿½ï¿½ï¿½ ?ï¿½ï¿½ï¿½ï¿½ ï¿½Ù²ï¿½ ï¿½ï¿½Ð¹ï¿½È£ï¿½ï¿½ ï¿½Ö°ï¿½  2ï¿½ï¿½ï¿½ï¿½ ? ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Ìµï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Þ¾Æ³Ö´Â´ï¿½.
		   int result = 0;
		   
		   try {
			   
			con = getConnection();
			
			psmt = con.prepareStatement(chkpw);
			psmt.setString(1, MEMBER_ID);
			rs = psmt.executeQuery();
			
			if(chpwd.equals(chkpwd)){
				
			while(rs.next()){
				
			if(rs.getString("MEMBER_PW").equals(nowpwd)){
				
				
				psmt = con.prepareStatement(changepwd);
				psmt.setString(1, chpwd);
				psmt.setString(2, MEMBER_ID);
				result = psmt.executeUpdate();
				
				if(result ==1 ){
					System.out.println("ï¿½ï¿½Ð¹ï¿½È£ï¿½ï¿½ ï¿½ï¿½Ä¡ï¿½Õ´Ï´ï¿½");
				}else{
					System.out.println("ï¿½ï¿½Ð¹ï¿½È£ï¿½ï¿½ ï¿½ï¿½Ä¡ï¿½ï¿½ï¿½ï¿½ ï¿½Ê½ï¿½ï¿½Ï´ï¿½.");
				}
				
			
		}else{
			System.out.println("ï¿½ï¿½Ð¹ï¿½È£ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½Ê½ï¿½ï¿½Ï´ï¿½.");
		}
		}//while ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½.
		}else{
			System.out.println("ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½Ð¹ï¿½È£ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½Ê½ï¿½ï¿½Ï´ï¿½.");
		}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            try {
                if(rs != null)rs.close();
                if(psmt != null) psmt.close();
                if(con != null) con.close();
             } catch (SQLException e) {
             }
       }
		return result;
 }
///////////////////////////ï¿½ï¿½ï¿½ï¿½ È¸ï¿½ï¿½Å»ï¿½ï¿½.////////////////////////
		   public int outUser(String MEMBER_ID){
			   
//			   SimpleDateFormat outDate = new SimpleDateFormat("yyyyï¿½ï¿½ MMï¿½ï¿½ddï¿½ï¿½ HHï¿½ï¿½mmï¿½ï¿½");
//			   Date date = new Date();
//			   String today = outDate.format(date);
			   
			   long outTime = System.currentTimeMillis()+60*60*24*1000*7;
			   
			   System.out.println(outTime * 60*60*24*1000*7);
			   
			   
			   String delUser = "UPDATE member SET OUT_TIME=? WHERE MEMBER_ID =?";
			   int result = 0;
			   try {
				con = getConnection();
				psmt = con.prepareStatement(delUser);
				psmt.setLong(1, outTime);
				psmt.setString(2, MEMBER_ID);
				result = psmt.executeUpdate();
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}finally {
	            try {
	                if(rs != null)
	                if(psmt != null) psmt.close();
	                if(con != null) con.close();
	             } catch (SQLException e) {
	             }
	       }
			   
			   return result;
		   }
/////////////////////////////////È¸ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ ï¿½ï¿½////////////////////////////////////////
		   public int changeInfo(String MEMBER_NAME, String MEMBER_PHONE, String MEMBER_BIRTH, String MEMBER_ID){
			   String changeInfo = "UPDATE MEMBER set MEMBER_NAME=?, MEMBER_PHONE=?,MEMBER_BIRTH=? where MEMBER_ID =?";
			   int result = 0;
			   try {
				con = getConnection();
				psmt = con.prepareStatement(changeInfo);
				
				psmt.setString(1, MEMBER_NAME);
				psmt.setString(2, MEMBER_PHONE);
				psmt.setString(3, MEMBER_BIRTH);
				psmt.setString(4, MEMBER_ID);
				result = psmt.executeUpdate();
				
				if(result != 1){
					System.out.println("È¸ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Ç¾ï¿½ï¿½ï¿½.");
					System.out.println("ï¿½ï¿½");
				}else{
					System.out.println("È¸ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ÈµÇ¾ï¿½ï¿½ï¿½.");
					System.out.println("ï¿½ï¿½2");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
	            try {
	                if(rs != null)
	                if(psmt != null) psmt.close();
	                if(con != null) con.close();
	             } catch (SQLException e) {
	             }
	       }
			   
			   return result;
			   
			   
		   }
		  		   
////////////////////////////////ï¿½Ì¹ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½èº¸ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½Ï±ï¿½.//////////////////////////////////////
		public int changeImg(String MEMBER_ID, String MEMBER_IMG){
			String changepwd = "update MEMBER set MEMBER_IMG = ? where MEMBER_ID = ?";
			int result = 0;
			
			 try {
					con = getConnection();
					
					psmt = con.prepareStatement(changepwd);
					psmt.setString(1, "img/" + MEMBER_IMG);
					psmt.setString(2, MEMBER_ID);
					result = psmt.executeUpdate();
						
					

				} catch (Exception e) {
					e.printStackTrace();
				}finally {
		            try {
		                if(rs != null)rs.close();
		                if(psmt != null) psmt.close();
		                if(con != null) con.close();
		             } catch (SQLException e) {
		             }
		       }
				return result;
			
		}
<<<<<<< HEAD
///////////////////Ä£±¸¸ñ·Ï¿¡¼­ Ä£±¸Ã£±â/////////////////////////////////////////
		public ArrayList<MemberVO> findFriends(String userPhone){
			//Ä£±¸ ÀüÈ­¹øÈ£¸¦ °¡Áö°í Ä£±¸¸¦ °¡Á®¿Â´Ù.
=======
///////////////////Ä£ï¿½ï¿½ï¿½ï¿½Ï¿ï¿½ï¿½ï¿½ Ä£ï¿½ï¿½ ï¿½ß°ï¿½ï¿½Ï±ï¿½/////////////////////////////////////////
		public ArrayList<MemberVO> addFriends(String userPhone){
			//Ä£ï¿½ï¿½ ï¿½ï¿½È­ï¿½ï¿½È£ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ Ä£ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Â´ï¿½.
>>>>>>> 9ae82ddb5a8c7d7239a68d60a4fb8d266ffef528
			String addFriend = "select MEMBER_ID, MEMBER_NAME, MEMBER_BIRTH, MEMBER_PHONE, MEMBER_IMG from MEMBER where MEMBER_PHONE = ?";
			ArrayList<MemberVO> arList = new ArrayList<MemberVO>();
			try {
				con =getConnection();
				psmt = con.prepareStatement(addFriend);
				psmt.setString(1, userPhone);
				rs = psmt.executeQuery();
				
				while(rs.next()){
					MemberVO member = new MemberVO();
					member.setMEMBER_IMG(rs.getString(5));
					member.setMEMBER_NAME(rs.getString(2));
					member.setMEMBER_BIRTH(rs.getString(3));
					member.setMEMBER_PHONE(rs.getString(4));
					member.setMEMBER_ID(rs.getString(1));
					
					arList.add(member);
					
				
			
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
	            try {
	                if(rs != null)rs.close();
	                if(psmt != null) psmt.close();
	                if(con != null) con.close();
	             } catch (SQLException e) {
	             }
	       }
			
			return arList;
		}	
		/////////////////////////////Ä£±¸¸¦ Ãß°¡ÇÏ±â./////////////////////////////
		public ArrayList<MemberVO> plusFriend(String userPhone, String MEMBER_ID){
			int result = 0;
			String findMyFriend = "select FRIENDS_LIST from MEMBER where MEMBER_ID=?";
			String searchUserId = "select MEMBER_ID from MEMBER where MEMBER_PHONE=?";
			String plusUser = "update MEMBER set FRIENDS_LIST = ? where MEMBER_ID = ?";
			   //1¹ø¤Š ?¿¡´Â Ä£±¸¾ÆÀÌµð¸¦ ³Ö´Â´Ù ','ÇØ¼­ ³Ö¾î¾ßÇÔ. µÎ¹øÂ° ? ¿¡´Â ¼¼¼Ç¹Þ¾Æ¿Â ¾ÆÀÌµð¸¦ ³Ö´Â´Ù.
			ArrayList<MemberVO> arList = new ArrayList<MemberVO>();
		try {
			con = getConnection();
			psmt = con.prepareStatement(findMyFriend);
			psmt.setString(1, MEMBER_ID);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				//ÀÌ°Ô ³ªÀÇ Ä£±¸¸ñ·ÏÀÌ Âß³ª¿Â´Ù.
//				MemberVO mvo = new MemberVO();
//				for(int i=0; i < arList.size(); i++){
//					mvo.setMEMBER_ID(rs.getString(i));
//				}
				
				
				String myList = rs.getString(1);
				psmt = con.prepareStatement(searchUserId);
				psmt.setString(1, userPhone);
				rs = psmt.executeQuery();
			while(rs.next()){
			//¼¼¼ÇÀ» ¹Þ¾Æ¼­ ¾ÆÀÌµð³Ö°í ±× Ä£±¸ ¸ñ·ÏÀ» »Ì¾Æ¿Â´ÙÀ½¿¡ ±× Ä£±¸¸ñ·Ï ¿¡ , Ãß°¡ÇØ¼­ 
				if(findMyFriend != rs.getString(1)){
				psmt = con.prepareStatement(plusUser);
				psmt.setString(1, myList + ',' + rs.getString(1));
				psmt.setString(2, MEMBER_ID);
				result = psmt.executeUpdate();
				
				while(rs.next()){
					MemberVO member = new MemberVO();
					member.setMEMBER_IMG(rs.getString(5));
					member.setMEMBER_NAME(rs.getString(2));
					member.setMEMBER_BIRTH(rs.getString(3));
					member.setMEMBER_PHONE(rs.getString(4));
					member.setMEMBER_ID(rs.getString(1));
					
					arList.add(member);
				}
				
				}else{
					System.out.println("ÀÌ¹Ì Ä£±¸°¡ ÀÖ½À´Ï´Ù.");
					
				}
				
			
			}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
            try {
                if(rs != null)rs.close();
                if(psmt != null) psmt.close();
                if(con != null) con.close();
             } catch (SQLException e) {
             }
       }
		
		
		
			return arList;
		}
		
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		public void deleteFriend(){
			String deleteFriend = "";
			
			
		}
	}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		