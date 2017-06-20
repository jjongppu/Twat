package com.twat.dto;

import java.sql.Timestamp;

public class MyCalendarVO {
	private int myCalIndex;
	private String memberId;
	private Timestamp myCalDate;
	private String myCalContents;
	public MyCalendarVO(int myCalIndex, String memberId, Timestamp myCalDate, String myCalContents) {
		super();
		this.myCalIndex = myCalIndex;
		this.memberId = memberId;
		this.myCalDate = myCalDate;
		this.myCalContents = myCalContents;
	}
	public int getMyCalIndex() {
		return myCalIndex;
	}
	public void setMyCalIndex(int myCalIndex) {
		this.myCalIndex = myCalIndex;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Timestamp getMyCalDate() {
		return myCalDate;
	}
	public void setMyCalDate(Timestamp myCalDate) {
		this.myCalDate = myCalDate;
	}
	public String getMyCalContents() {
		return myCalContents;
	}
	public void setMyCalContents(String myCalContents) {
		this.myCalContents = myCalContents;
	}
	@Override
	public String toString() {
		return "MyCalendar [myCalIndex=" + myCalIndex + ", memberId=" + memberId + ", myCalDate=" + myCalDate
				+ ", myCalContents=" + myCalContents + "]";
	}
	
	
}
