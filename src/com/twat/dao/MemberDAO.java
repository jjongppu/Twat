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
	
	
	// MemberDAO 占쏙옙 占싱깍옙占쏙옙 -----------------------------------
	private static MemberDAO instance = new MemberDAO();
	
	private MemberDAO(){}
	
	public static MemberDAO getInstance(){
		return instance;
	}
	 
	// -------------------------------------------------------
	
	
	
	   // DB占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 con占쏙옙 占쏙옙환占싹댐옙 占쌨쇽옙占쏙옙 --------------------------------------------
	   public Connection getConnection() throws Exception {
	         Context initCtx = new InitialContext();
	         DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
	         
	      return ds.getConnection();
	   }

	
	
	  // 占쏙옙占싱듸옙 찾占쏙옙 占쌨쇽옙占쏙옙 (占싱몌옙,占쏙옙화占쏙옙호占쏙옙 찾占쏙옙)-----占승울옙-------------------------
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
	   
	   
	   
// 占쏙옙橘占싫� 찾占쏙옙 占쌨쇽옙占쏙옙 ---------占승울옙-------------------------------
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
	   

		// 회占쏙옙 占싸깍옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쌨쇽옙占쏙옙 ----------------------------------
		public int loginMember(String MEMBER_ID, String MEMBER_PW) {
			int result = -1;
			
			long currentTime = System.currentTimeMillis();
			   
			System.out.println(currentTime);
			
//			String selectSql = "select OUT_TIME from MEMBER where MEMBER_ID = ? and MEMBER_PW = ?";
			String selectSql = "select OUT_TIME from MEMBER where MEMBER_ID = ? and AES_DECRYPT(UNHEX(MEMBER_PW), 'memPW') = ?";
			
			try {
				con = getConnection();
				psmt = con.prepareStatement(selectSql);
				psmt.setString(1, MEMBER_ID);
				psmt.setString(2, MEMBER_PW);
//				System.out.println("!");
				rs = psmt.executeQuery();
				
//				if(rs.next()) {
//					// out_time占쏙옙 0占싱몌옙 占싸깍옙占쏙옙 占쏙옙占쏙옙 result占쏙옙 1占쌍억옙占쏙옙
//					if(rs.getInt(1) == 0) {
//						// 占싸깍옙占쏙옙 占쏙옙占쏙옙
//						result = 1;
//					} else {
//						result = rs.getInt(1);
//						System.out.println(result);
//					}
//				}
				
				if(rs.next()) {
					// out_time占쏙옙 0占싱몌옙 占싸깍옙占쏙옙 占쏙옙占쏙옙 result占쏙옙 1占쌍억옙占쏙옙
					if(rs.getLong(1) == 0) {
						// 占싸깍옙占쏙옙 占쏙옙占쏙옙
						result = 1;
					} else if(rs.getLong(1) > currentTime) { // 탈占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 회占쏙옙
						result = -1;
//						System.out.println(result);
					} else if(rs.getLong(1) < currentTime) { // 탈占쏙옙占� 회占쏙옙
						result = -2;
					} else { // 占쏙옙占싱듸옙, 占쏙옙占� 틀占쏙옙占쏙옙 占쏙옙占쏙옙
						result = 0;
					}
				}
			} catch (Exception e) {
				System.out.print("占쏙옙占쏙옙 占쏙옙占쏙옙");
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
	   
	   
	   
	// 占싸깍옙占쏙옙 占싼삼옙占쏙옙占� 占쌓뤄옙 占쏙옙占쏙옙占쏙옙 占싻기리
			public ArrayList<Integer> getMyGroupList(String MEMBER_ID) {
				ArrayList<Integer> glList = new ArrayList<Integer>();
				
				
				try {
					con = getConnection();
					
					// 占쏙옙占싱듸옙占� 占쌓뤄옙占쏙옙 占쌀뤄옙占쏙옙占쏙옙
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


	   
	   
// 회占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쌨쇽옙占쏙옙 ------占승울옙----------------------------

	   public int signUpMember(String MEMBER_ID, String MEMBER_PW, String MEMBER_NAME, String MEMBER_PHONE, 
			   String MEMBER_GENDER, String MEMBER_BIRTH, int OUT_TIME, String MEMBER_QUESTION, String MEMBER_ANSWER) {

	      
	      int result = 0;
	      int signUp = 0;
	      
//	      String insertSql = "insert into MEMBER values(?,?,?,?,?,?,?,?,?,?)";

//	      String insertSql = "insert into MEMBER values(?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,?,?)";
	      String insertSql = "insert into MEMBER values(?,HEX(AES_ENCRYPT(?, 'memPW')),?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,?,?)";
	      
	      try {
	         con = getConnection();
	         psmt = con.prepareStatement(insertSql);
	      
	         psmt.setString(1, MEMBER_ID);
	         psmt.setString(2,MEMBER_PW);
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
	   

	   
	   
// 회占쏙옙占쏙옙占쌉쏙옙 占쏙옙占싱듸옙 占실시곤옙 占싯삼옙...---------占승울옙------------------------------
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
	   
	   
	   

	
	// 친占쏙옙 占쏙옙占쏙옙占� 占싱아울옙占쏙옙槁占쏙옙求占� 占쌨쇽옙占쏙옙 -----占쏙옙占쏙옙 ---------------------
	public ArrayList printFriendList(String MEMBER_ID, String MEMBER_NAME, String MEMBER_BIRTH, String MEMBER_PHONE, String MEMBER_IMG){
		 

		String sql = "SELECT MEMBER_IMG, MEMBER_NAME, MEMBER_BIRTH, MEMBER_PHONE, FRIENDS_LIST FROM MEMBER WHERE MEMBER_ID = 'asdfasdf'";
		//占싸깍옙占싸쇽옙占쏙옙 占쏙옙占싱듸옙 占쌨아와쇽옙 占쌍깍옙.
		ArrayList arList = new ArrayList();
		
		HttpSession session = null;
	    session.getAttribute(MEMBER_ID);
		
		try {
			con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				// 친占쏙옙占쏙옙占쏙옙트占쏙옙 占심곤옙占쏙옙 str占쏙옙 占쏙옙占쏙옙斂占�
				String[] str = rs.getString(9).split(",");
				System.out.println(str);
				
				// str占쏙옙占쏙옙 b占쏙옙 占쌍댐옙占쏙옙 占싯아븝옙占쏙옙
				for(int i=0; i<arList.size(); i++){
					// 占쏙옙占쏙옙 b占쏙옙 占쌍다몌옙 memberVO占쏙옙체占쏙옙 占쏙옙占쏙옙底� rs占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 arList占쏙옙 add
					if(str[i] == MEMBER_ID) {	
						MemberVO mdo = new MemberVO();
					arList.add(mdo.getMEMBER_NAME());					
					
				}else{
					System.out.println("친占쏙옙占쏙옙 占쏙옙占쏙옙占싹댐옙.");
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
	
	   // 친占쏙옙占쏙옙占� 占쏙옙占싶쇽옙 친占쏙옙占쏙옙占쏙옙占쏙옙 占싼곤옙占쌍깍옙 占쏙옙占쏙옙ver....
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
	               
	               // 친占쏙옙占쏙옙占� 占쏙옙占쏙옙占� 占쏙옙占쏙옙占쌜쇽옙占싹는곤옙 占쏙옙占쏙옙占쏙옙 db占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙占� 占쏙옙占쏙옙占실뤄옙,,,占쌥아뱄옙占쏙옙.,,
//	               try {
//	                  if(rs != null)
//	                  if(psmt != null) psmt.close();
//	                  if(con != null) con.close();
//	               } catch (SQLException e) {
//	                  e.printStackTrace();
//	               }
	               //친占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占시뤄옙占쏙옙占쏙옙占쏙옙..
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
	   
	 //회占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙槁占쏙옙占�---------------占쏙옙占쏙옙--------------------------------
	   public ArrayList<MemberVO> myInfo(String MEMBER_ID){
	      String sql = "select MEMBER_IMG, MEMBER_NAME, MEMBER_PHONE, MEMBER_BIRTH from MEMBER where MEMBER_ID = ? ";
	      
	      ArrayList<MemberVO> arList = new ArrayList<MemberVO>(); 
	      
	      try {
	         con = getConnection();
	          psmt = con.prepareStatement(sql);
	            psmt.setString(1, MEMBER_ID);
	            rs = psmt.executeQuery();
	            
	            //占싱뤄옙占쏙옙占싹몌옙占쏙옙 rs.getstring(1)占쏙옙 占싱뱄옙占쏙옙 2占쏙옙 占싱몌옙 3占쏙옙 占쏙옙화占쏙옙호 4占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占승댐옙.
	            while(rs.next()){
	               MemberVO mvo = new MemberVO();
	               mvo.setMEMBER_IMG(rs.getString("MEMBER_IMG"));
	               mvo.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
	               mvo.setMEMBER_PHONE(rs.getString("MEMBER_PHONE"));
	               mvo.setMEMBER_BIRTH(rs.getString("MEMBER_BIRTH"));
	                 arList.add(mvo);
	            }
	            //arList占쏙옙 占싱뱄옙占쏙옙 占싱몌옙 占쏙옙占쏙옙호 占쏙옙占쏙옙占쏙옙 占쏙옙占승댐옙.
	         
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

// 회占쏙옙占쏙옙占쏙옙 占썼열占쏙옙 占쏙옙占쏙옙 회占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙환占싹댐옙 占쌉쇽옙, 占썼열占쏙옙 占쏙옙환占싹몌옙 占쏙옙占승댐옙 占쏙옙占쏙옙+占싱몌옙占쏙옙占쏙옙 占쏙옙占쏙옙
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
					   // memberList占쏙옙 ID占쏙옙 member_id占쏙옙占쏙옙 占쏙옙占쌕몌옙 member 占쏙옙체 占쏙옙占쏙옙占쌔쇽옙 arrList占쏙옙 占쌩곤옙
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

///////////////////////////////////////////////////////////////////占쏙옙占쏙옙 占쏙옙橘占싫� 占쏙옙占쏙옙.
	   
	   public int changePw(String MEMBER_ID, String nowpwd, String chpwd, String chkpwd){
		   
		   
		   
//		   String chkpw = "select MEMBER_PW from MEMBER where MEMBER_ID = ?";
		   String chkpw = "select AES_DECRYPT(UNHEX(MEMBER_PW) from MEMBER where MEMBER_ID = ?";
		   
		   // ?占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 占쌨아울옙 占쏙옙占싱듸옙 占쌍곤옙 占쏙옙橘占싫ｏ옙占� 占쌨아온댐옙占쏙옙占쏙옙 占쌨아울옙 占쏙옙橘占싫ｏ옙占� 占쏙옙占쏙옙占싻뱄옙호 占쌉력띰옙占싱띰옙 占쏙옙占쌔쇽옙 占쏙옙占쏙옙占쏙옙 占싸뱄옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙 占싼어가占쏙옙 占쏙옙橘占싫ｏ옙占� 占쌕뀐옙占쌔댐옙.
		   String changepwd = "update MEMBER set MEMBER_PW = (HEX(AES_ENCRYPT(?, 'memPw')) where MEMBER_ID = ?";
		   //1占쏙옙占쏙옙 ?占쏙옙占쏙옙 占쌕뀐옙 占쏙옙橘占싫ｏ옙占� 占쌍곤옙  2占쏙옙占쏙옙 ? 占쏙옙占쏙옙 占쏙옙占싱듸옙 占쏙옙占쏙옙占쏙옙占쏙옙 占쌨아넣는댐옙.
		   int result = 0;
		   
		   try {
			   
			con = getConnection();
			
			psmt = con.prepareStatement(chkpw);
			psmt.setString(1, MEMBER_ID);
			rs = psmt.executeQuery();
			
			if(chpwd.equals(chkpwd)){
				
			while(rs.next()){
				
			if(rs.getString("AES_DECRYPT(UNHEX(MEMBER_PW), 'memPw')").equals(nowpwd)){
				
				
				psmt = con.prepareStatement(changepwd);
				psmt.setString(1, chpwd);
				psmt.setString(2, MEMBER_ID);
				result = psmt.executeUpdate();
				
				if(result ==1 ){
					System.out.println("占쏙옙橘占싫ｏ옙占� 占쏙옙치占쌌니댐옙");
				}else{
					System.out.println("占쏙옙橘占싫ｏ옙占� 占쏙옙치占쏙옙占쏙옙 占십쏙옙占싹댐옙.");
				}
				
			
		}else{
			System.out.println("占쏙옙橘占싫ｏ옙占� 占쏙옙占쏙옙 占쏙옙占쏙옙 占십쏙옙占싹댐옙.");
		}
		}//while 占쏙옙占쏙옙占쏙옙 占쏙옙.
		}else{
			System.out.println("占쏙옙占쏙옙占쏙옙 占쏙옙橘占싫ｏ옙占� 占쏙옙占쏙옙 占쏙옙占쏙옙 占십쏙옙占싹댐옙.");
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
///////////////////////////占쏙옙占쏙옙 회占쏙옙탈占쏙옙.////////////////////////
		   public int outUser(String MEMBER_ID){
			   
//			   SimpleDateFormat outDate = new SimpleDateFormat("yyyy占쏙옙 MM占쏙옙dd占쏙옙 HH占쏙옙mm占쏙옙");
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
/////////////////////////////////회占쏙옙占쏙옙占쏙옙 占쏙옙 占쏙옙////////////////////////////////////////
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
					System.out.println("회占쏙옙 占쏙옙占쏙옙占실억옙占쏙옙.");
					System.out.println("占쏙옙");
				}else{
					System.out.println("회占쏙옙 占쏙옙占쏙옙占싫되억옙占쏙옙.");
					System.out.println("占쏙옙2");
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
		  		   
////////////////////////////////占싱뱄옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占썼보占쏙옙 占쏙옙占쏙옙 占싹깍옙.//////////////////////////////////////
		public int changeImg(String MEMBER_ID, String MEMBER_IMG){
			String changepwd = "update MEMBER set MEMBER_IMG = ? where MEMBER_ID = ?";
			int result = 0;
			
			 try {
					con = getConnection();
					
					psmt = con.prepareStatement(changepwd);
					psmt.setString(1, "img/member/" + MEMBER_IMG);
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

///////////////////친구목록에서 친구찾기/////////////////////////////////////////
		public ArrayList<MemberVO> findFriends(String userPhone){
			//친구 전화번호를 가지고 친구를 가져온다.


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
		/////////////////////////////친구를 추가하기./////////////////////////////
		public ArrayList<MemberVO> plusFriend(String userPhone, String MEMBER_ID){
			int result = 0;
			String findMyFriend = "select FRIENDS_LIST from MEMBER where MEMBER_ID=?";
			String searchUserId = "select MEMBER_ID from MEMBER where MEMBER_PHONE=?";
			String plusUser = "update MEMBER set FRIENDS_LIST = ? where MEMBER_ID = ?";
			   //1번쨰 ?에는 친구아이디를 넣는다 ','해서 넣어야함. 두번째 ? 에는 세션받아온 아이디를 넣는다.
			ArrayList<MemberVO> arList = new ArrayList<MemberVO>();
		try {
			con = getConnection();
			psmt = con.prepareStatement(findMyFriend);
			psmt.setString(1, MEMBER_ID);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				//이게 나의 친구목록이 쭉나온다.
//				MemberVO mvo = new MemberVO();
//				for(int i=0; i < arList.size(); i++){
//					mvo.setMEMBER_ID(rs.getString(i));
//				}
				
				
				String myList = rs.getString(1);
				psmt = con.prepareStatement(searchUserId);
				psmt.setString(1, userPhone);
				rs = psmt.executeQuery();
			while(rs.next()){
			//세션을 받아서 아이디넣고 그 친구 목록을 뽑아온다음에 그 친구목록 에 , 추가해서 
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
					System.out.println("이미 친구가 있습니다.");
					
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
		
	}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		