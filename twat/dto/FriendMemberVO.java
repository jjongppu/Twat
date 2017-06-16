package com.twat.dto;

import java.sql.Timestamp;

public class FriendMemberVO {
	private String MEMBER_ID;
	private String MEMBER_PW;
	private String MEMBER_NAME;
	private String MEMBER_PHONE;
	private String MEMBER_IMG;
	private String MEMBER_GENDER;
	private String MEMBER_BIRTH;
	private long MEMBER_CAL;
	private long FRIENDS_LIST;
	private Timestamp OUT_TIME;
	
	public FriendMemberVO(){}
	
	public FriendMemberVO(String mEMBER_ID, String mEMBER_PW, String mEMBER_NAME, String mEMBER_PHONE,
			String mEMBER_IMG, String mEMBER_GENDER, String mEMBER_BIRTH, long mEMBER_CAL, long fRIENDS_LIST,
			Timestamp oUT_TIME) {
		super();
		MEMBER_ID = mEMBER_ID;
		MEMBER_PW = mEMBER_PW;
		MEMBER_NAME = mEMBER_NAME;
		MEMBER_PHONE = mEMBER_PHONE;
		MEMBER_IMG = mEMBER_IMG;
		MEMBER_GENDER = mEMBER_GENDER;
		MEMBER_BIRTH = mEMBER_BIRTH;
		MEMBER_CAL = mEMBER_CAL;
		FRIENDS_LIST = fRIENDS_LIST;
		OUT_TIME = oUT_TIME;
	}
	
	public FriendMemberVO(String mEMBER_IMG, String mEMBER_NAME, String mEMBER_BIRTH, String mEMBER_PHONE){
		super();
		MEMBER_IMG = mEMBER_IMG;
		MEMBER_NAME = mEMBER_NAME;
		MEMBER_BIRTH = mEMBER_BIRTH;
		MEMBER_PHONE = mEMBER_PHONE;
	}





	public String getMEMBER_ID() {
		return MEMBER_ID;
	}

	public void setMEMBER_ID(String mEMBER_ID) {
		MEMBER_ID = mEMBER_ID;
	}

	public String getMEMBER_PW() {
		return MEMBER_PW;
	}

	public void setMEMBER_PW(String mEMBER_PW) {
		MEMBER_PW = mEMBER_PW;
	}

	public String getMEMBER_NAME() {
		return MEMBER_NAME;
	}

	public void setMEMBER_NAME(String mEMBER_NAME) {
		MEMBER_NAME = mEMBER_NAME;
	}

	public String getMEMBER_PHONE() {
		return MEMBER_PHONE;
	}

	public void setMEMBER_PHONE(String mEMBER_PHONE) {
		MEMBER_PHONE = mEMBER_PHONE;
	}

	public String getMEMBER_IMG() {
		return MEMBER_IMG;
	}

	public void setMEMBER_IMG(String mEMBER_IMG) {
		MEMBER_IMG = mEMBER_IMG;
	}

	public String getMEMBER_GENDER() {
		return MEMBER_GENDER;
	}

	public void setMEMBER_GENDER(String mEMBER_GENDER) {
		MEMBER_GENDER = mEMBER_GENDER;
	}

	public String getMEMBER_BIRTH() {
		return MEMBER_BIRTH;
	}

	public void setMEMBER_BIRTH(String mEMBER_BIRTH) {
		MEMBER_BIRTH = mEMBER_BIRTH;
	}

	public long getMEMBER_CAL() {
		return MEMBER_CAL;
	}

	public void setMEMBER_CAL(long mEMBER_CAL) {
		MEMBER_CAL = mEMBER_CAL;
	}

	public long getFRIENDS_LIST() {
		return FRIENDS_LIST;
	}

	public void setFRIENDS_LIST(long fRIENDS_LIST) {
		FRIENDS_LIST = fRIENDS_LIST;
	}

	public Timestamp getOUT_TIME() {
		return OUT_TIME;
	}

	public void setOUT_TIME(Timestamp oUT_TIME) {
		OUT_TIME = oUT_TIME;
	}

	@Override
	public String toString() {
		return "FriendMemberVO [MEMBER_ID=" + MEMBER_ID + ", MEMBER_PW=" + MEMBER_PW + ", MEMBER_NAME=" + MEMBER_NAME
				+ ", MEMBER_PHONE=" + MEMBER_PHONE + ", MEMBER_IMG=" + MEMBER_IMG + ", MEMBER_GENDER=" + MEMBER_GENDER
				+ ", MEMBER_BIRTH=" + MEMBER_BIRTH + ", MEMBER_CAL=" + MEMBER_CAL + ", FRIENDS_LIST=" + FRIENDS_LIST
				+ ", OUT_TIME=" + OUT_TIME + "]";
	}

	public void getFRIENDS_LIST(String[] str) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
}
