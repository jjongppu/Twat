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
		public long loginMember(String MEMBER_ID, String MEMBER_PW) {
			long result = -1;
			
			long currentTime = System.currentTimeMillis();
			   
			System.out.println(currentTime);
			
//			String selectSql = "select OUT_TIME from MEMBER where MEMBER_ID = ? and MEMBER_PW = ?";
			String selectSql = "select OUT_TIME from MEMBER where MEMBER_ID = ? and AES_DECRYPT(UNHEX(MEMBER_PW), 'memPW') = ?";
			
			try {
				con = getConnection();
				psmt = con.prepareStatement(selectSql);
				psmt.setString(1, MEMBER_ID);
				psmt.setString(2, MEMBER_PW);
				rs = psmt.executeQuery();
				if(rs.next()) {
						result = rs.getLong(1);
				}
			} catch (Exception e) {
				System.out.print("占쏙옙占쏙옙 占쏙옙占쏙옙");
				e.printStackTrace();
			} finally {
					try {
						if(rs != null)rs.close();
						if(psmt != null) psmt.close();
						if(con != null) con.close();
					} catch (SQLException e) {
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
	   
//	   public int changePw(String MEMBER_ID, String nowpwd, String chpwd, String chkpwd){
//		   
//		   
//		   
//		   String chkpw = "select MEMBER_PW from MEMBER where MEMBER_ID = ?";
////		   String chkpw = "select AES_DECRYPT(UNHEX(MEMBER_PW) from MEMBER where MEMBER_ID = ?";
//		   
//		   // ?占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 占쌨아울옙 占쏙옙占싱듸옙 占쌍곤옙 占쏙옙橘占싫ｏ옙占� 占쌨아온댐옙占쏙옙占쏙옙 占쌨아울옙 占쏙옙橘占싫ｏ옙占� 占쏙옙占쏙옙占싻뱄옙호 占쌉력띰옙占싱띰옙 占쏙옙占쌔쇽옙 占쏙옙占쏙옙占쏙옙 占싸뱄옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙 占싼어가占쏙옙 占쏙옙橘占싫ｏ옙占� 占쌕뀐옙占쌔댐옙.
//		   String changepwd = "update MEMBER set MEMBER_PW = ? where MEMBER_ID = ?";
//		   //1占쏙옙占쏙옙 ?占쏙옙占쏙옙 占쌕뀐옙 占쏙옙橘占싫ｏ옙占� 占쌍곤옙  2占쏙옙占쏙옙 ? 占쏙옙占쏙옙 占쏙옙占싱듸옙 占쏙옙占쏙옙占쏙옙占쏙옙 占쌨아넣는댐옙.
//		   int result = 0;
//		   
//		   try {
//			   
//			con = getConnection();
//			
//			psmt = con.prepareStatement(chkpw);
//			psmt.setString(1, MEMBER_ID);
//			rs = psmt.executeQuery();
//			
//			if(chpwd.equals(chkpwd)){
//				
//			while(rs.next()){
//				
//			if(rs.getString("MEMBER_PW").equals(nowpwd)){
//				
//				
//				psmt = con.prepareStatement(changepwd);
//				psmt.setString(1, chpwd);
//				psmt.setString(2, MEMBER_ID);
//				result = psmt.executeUpdate();
//				
//				if(result ==1 ){
//					System.out.println("占쏙옙橘占싫ｏ옙占� 占쏙옙치占쌌니댐옙");
//				}else{
//					System.out.println("占쏙옙橘占싫ｏ옙占� 占쏙옙치占쏙옙占쏙옙 占십쏙옙占싹댐옙.");
//				}
//				
//			
//		}else{
//			System.out.println("占쏙옙橘占싫ｏ옙占� 占쏙옙占쏙옙 占쏙옙占쏙옙 占십쏙옙占싹댐옙.");
//		}
//		}//while 占쏙옙占쏙옙占쏙옙 占쏙옙.
//		}else{
//			System.out.println("占쏙옙占쏙옙占쏙옙 占쏙옙橘占싫ｏ옙占� 占쏙옙占쏙옙 占쏙옙占쏙옙 占십쏙옙占싹댐옙.");
//		}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//            try {
//                if(rs != null)rs.close();
//                if(psmt != null) psmt.close();
//                if(con != null) con.close();
//             } catch (SQLException e) {
//             }
//       }
//		return result;
// }
	   

//	   public int changePw(String MEMBER_ID, String nowpwd){
//		   con = getConnection();
//		   String sql = "select "
//		   PreparedStatement psmt2 = con.prepareStatement(sql);
//	   }
	   
	   
	   
///////////////////////////占쏙옙占쏙옙 회占쏙옙탈占쏙옙.////////////////////////
		   public int outUser(String MEMBER_ID,String state){
			   
//			   SimpleDateFormat outDate = new SimpleDateFormat("yyyy占쏙옙 MM占쏙옙dd占쏙옙 HH占쏙옙mm占쏙옙");
//			   Date date = new Date();
//			   String today = outDate.format(date);
			   
			   long outTime = System.currentTimeMillis()+60*60*24*1000*7;
			   long resetTime = 0;
			   
			   
			   
			   String delUser = "UPDATE member SET OUT_TIME=? WHERE MEMBER_ID =?";
				   
			   
			   int result = 0;
			   try {
				con = getConnection();
				psmt = con.prepareStatement(delUser);
				if(state.equals("out")){
					psmt.setLong(1, outTime);
					System.out.println("1");
				}else{
					psmt.setLong(1, resetTime);
					System.out.println("2");
				}
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
		
		
		
		public ArrayList<String> friendPhoneSearch(String phoneNum){
			ArrayList<String> memArr = new ArrayList<String>();
			PreparedStatement psmt2 = null;
			ResultSet rs2 = null;
			try {
				con = getConnection();
								 
				String Sql = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_PHONE, MEMBER_IMG, MEMBER_GENDER, MEMBER_BIRTH FROM MEMBER WHERE MEMBER_PHONE = ?";
				psmt2 = con.prepareStatement(Sql);
				psmt2.setString(1, phoneNum);
				rs2 = psmt2.executeQuery();
				
				while(rs2.next()){
					memArr.add(rs2.getString("MEMBER_ID"));
					memArr.add(rs2.getString("MEMBER_NAME"));					
					memArr.add(rs2.getString("MEMBER_PHONE"));
					memArr.add(rs2.getString("MEMBER_IMG"));
					memArr.add(rs2.getString("MEMBER_GENDER"));
					memArr.add(rs2.getString("MEMBER_BIRTH"));
					
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
	            try {
	                if(rs2 != null)rs.close();
	                if(psmt2 != null) psmt.close();
	                if(con != null) con.close();
	             } catch (SQLException e) {
	             }
	       }
			
		
			return memArr;
			
		}
		
		
		public int requestFriend(String userId, String friendId){
			PreparedStatement psmt2 = null;
			ResultSet rs2 = null;
			ResultSet rs3 = null;
			String myFriendList = null;
			String friendFriendsList = null;
			int result = 0;
			
			try {
				con = getConnection();
				String sql ="select FRIENDS_LIST from member where MEMBER_ID = ?"; 
				psmt2 = con.prepareStatement(sql);
				psmt2.setString(1, userId);
				rs2 = psmt2.executeQuery();
				while(rs2.next())
					myFriendList = rs2.getString("FRIENDS_LIST"); //로그인한 유저의 친구 리스트
				
				
				psmt2.setString(1, friendId);				
				rs3 = psmt2.executeQuery();
				
				while(rs3.next())
					friendFriendsList = rs3.getString("FRIENDS_LIST"); // 친구의 친구 리스트
				
				
				
				
				System.out.println(myFriendList);
				System.out.println(friendFriendsList);
				
				for(int i=0; i<myFriendList.split(",").length; i++){
					if(myFriendList.split(",")[i].equals(friendId)){
						result = -1; // 이미 친구
						return result;
					}else if(myFriendList.split(",")[i].equals("*" + friendId)){
						result = 1; // 상대방에게 요청했을때
						return result;
					}else if(myFriendList.split(",")[i].equals("!" + friendId)){
						result = 2; // 나에게 온 친구요청이 있을때
						return result;
					}else if(userId.equals(friendId)){
						result = 3; // 본인 일때
						return result;
					}						
					
				}
				
				requestFriendsUpdate(userId, friendId, myFriendList, friendFriendsList);
				
				
				
				
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
	            try {
	                if(rs2 != null)rs.close();
	                if(rs3 != null)rs.close();
	                if(psmt2 != null) psmt.close();
	                if(con != null) con.close();
	             } catch (SQLException e) {
	             }
	       }
			
			return result;
			
			
		}
		
		public void requestFriendsUpdate(String userId, String friendId, String myFriendList, String friendFriendsList){
			PreparedStatement psmt2 = null;			
			
			System.out.println(myFriendList);
			
			
			try {
				String sql = "update member set FRIENDS_LIST = ? where MEMBER_ID = ?";
				psmt2 = con.prepareStatement(sql);
				psmt2.setString(1, myFriendList + "," + "*" + friendId);
				psmt2.setString(2, userId);
				psmt2.executeUpdate();
				
				String sql2 = "update member set FRIENDS_LIST = ? where MEMBER_ID = ?";
				psmt2.setString(1, friendFriendsList + "," + "!" + userId);
				psmt2.setString(2, friendId);
				psmt2.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
					try {
						if(psmt2 != null)
							psmt2.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
			
			
		}
		
		public MemberVO friendInfo(String friendId){
			PreparedStatement psmt2 = null;
			ResultSet rs2 = null;
			MemberVO member = new MemberVO();
			try {
				con = getConnection();
				String sql = "select * from member where MEMBER_ID = ?";
				psmt2 = con.prepareStatement(sql);
				psmt2.setString(1, friendId);
				rs2 = psmt2.executeQuery();
				while(rs2.next()){
					member.setMEMBER_ID(rs2.getString("MEMBER_ID"));
					member.setMEMBER_NAME(rs2.getString("MEMBER_NAME"));
					member.setMEMBER_IMG(rs2.getString("MEMBER_IMG"));
					member.setMEMBER_PHONE(rs2.getString("MEMBER_PHONE"));
					
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
					try {
						if(rs2 != null)
							rs2.close();
						if(psmt2 !=null)
							psmt2.close();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
			
			return member;
			
		}
		
		
		public ArrayList<MemberVO> requestingFriendList(String userId){
			PreparedStatement psmt2 = null;
			ResultSet rs2 = null;
			String friendsList = null;
			ArrayList<MemberVO> memberArr = new ArrayList<MemberVO>();
			try {
				con = getConnection();
				String sql ="select FRIENDS_LIST from member where MEMBER_ID = ?";
				psmt2 = con.prepareStatement(sql);
				psmt2.setString(1, userId);
				rs2 = psmt2.executeQuery();
//				System.out.println(rs2);
				
				
				while(rs2.next())
					friendsList = rs2.getString("FRIENDS_LIST");
				
//				System.out.println("내아이디 : " +userId);
//				System.out.println("친구리스트 : " + friendsList);
				if(friendsList.equals("") || friendsList.equals(",")){
					return null;
				}else{
					String[] eachFriend = friendsList.split(",");
					for(int i = 0; i < eachFriend.length; i++){
						
						if(eachFriend[i].length() != 0){
							if(eachFriend[i].substring(0, 1).equals("!") ){
								
//								System.out.println(friendInfo(eachFriend[i].substring(1, eachFriend[i].length())).getMEMBER_NAME());
								memberArr.add(friendInfo(eachFriend[i].substring(1, eachFriend[i].length())));
								
							}
							
							
						}
						
					
						
					}
					
				}
				
				
				
				
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				
				try {
					if(rs2 != null)
						rs2.close();					
					if(psmt2 != null)
						psmt2.close();
					if(con != null)
						con.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
			return memberArr;
			
			
			
			
		}
		public String getfriendListForString(String userId){
			
			PreparedStatement psmt2 = null;
			ResultSet rs2 = null;
			String friendList = "";
			String sql = "select FRIENDS_LIST FROM member where MEMBER_ID = ?";
			try {
				psmt2 = con.prepareStatement(sql);
				psmt2.setString(1, userId);
				rs2 = psmt2.executeQuery();
				if(rs2.next());
					friendList = rs2.getString("FRIENDS_LIST");
					
					
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
					try {
						if(rs2 != null)
							rs2.close();
						if(psmt2 != null)
							psmt2.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
			return friendList;
			
		}
		
		
		
		
		public void acceptFriend(String userId, String friendId){//친구요청 수락
			
			PreparedStatement psmt2 = null;
			String []myFriendList = null;
			String changeMyFriendList = "";
			String []friendFriendList =null;
			String changeFriendFriendList = "";
			
			try {
//				내친구목록 수정
				con = getConnection();
				String sql = "update member set FRIENDS_LIST = ? where MEMBER_ID = ?";
				psmt2 = con.prepareStatement(sql);
				
				myFriendList = getfriendListForString(userId).split(",");
				
				for(int i = 0; i < myFriendList.length; i++){
					if(myFriendList[i].length() != 0){
						if(myFriendList[i].equals("!" + friendId)){
							myFriendList[i] = myFriendList[i].substring(1, myFriendList[i].length());
							
						}
						
					}
						
					
				}
				
				for(int i = 0; i < myFriendList.length; i++){
					if(i == myFriendList.length - 1){
						changeMyFriendList += myFriendList[i];
						
					}else{
						changeMyFriendList += myFriendList[i] + ",";	
					}
					
					
				}
				
//				System.out.println(changeFriendList);
				
				psmt2.setString(1, changeMyFriendList);				
				psmt2.setString(2, userId);
				
				psmt2.executeUpdate();
				
//				System.out.println(changeMyFriendList);
				

				
				// 친구의 친구목록 수정
				String sql2 = "update member set FRIENDS_LIST = ? where MEMBER_ID = ?";
				psmt2 = con.prepareStatement(sql2);
				
				
				friendFriendList = getfriendListForString(friendId).split(",");
				for(int i = 0; i < friendFriendList.length; i++){
					if(friendFriendList[i].length() != 0){
						if(friendFriendList[i].equals("*" + userId)){
							friendFriendList[i] = friendFriendList[i].substring(1, friendFriendList[i].length());
							
						}
						
					}
						
					
				}
				
				
				for(int i = 0; i < friendFriendList.length; i++){
					if(i == friendFriendList.length - 1){
						changeFriendFriendList += friendFriendList[i];
						
					}else{
						changeFriendFriendList += friendFriendList[i] + ",";	
					}
					
					
				}
//				System.out.println(changeFriendFriendList);
				
				psmt2.setString(1, changeFriendFriendList);				
				psmt2.setString(2, friendId);
				
				psmt2.executeUpdate();
				
				
				
			
				
				
				
				
				
				
				
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
					try {
						if(psmt2!=null)
							psmt2.close();
						if(con!=null)
							con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
			
			
			
		}
		
		
}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		