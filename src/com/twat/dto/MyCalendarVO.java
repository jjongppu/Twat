package com.twat.dto;

import java.sql.Timestamp;

/**
 * @author greathoon
 *
 */
public class MyCalendarVO {
	private int my_cal_index;
	private String member_id;
	private Timestamp my_write_time;
	private String my_cal_contents;
	private String my_cal_date;
	private String my_cal_time;
	
	public MyCalendarVO(int my_cal_index, String member_id, Timestamp my_write_time, String my_cal_contents,
			String my_cal_date, String my_cal_time) {
		super();
		this.my_cal_index = my_cal_index;
		this.member_id = member_id;
		this.my_write_time = my_write_time;
		this.my_cal_contents = my_cal_contents;
		this.my_cal_date = my_cal_date;
		this.my_cal_time = my_cal_time;
	}

	public MyCalendarVO() {
		// TODO Auto-generated constructor stub
	}

	public int getMy_cal_index() {
		return my_cal_index;
	}

	public void setMy_cal_index(int my_cal_index) {
		this.my_cal_index = my_cal_index;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public Timestamp getMy_write_time() {
		return my_write_time;
	}

	public void setMy_write_time(Timestamp my_write_time) {
		this.my_write_time = my_write_time;
	}

	public String getMy_cal_contents() {
		return my_cal_contents;
	}

	public void setMy_cal_contents(String my_cal_contents) {
		this.my_cal_contents = my_cal_contents;
	}

	public String getMy_cal_date() {
		return my_cal_date;
	}

	public void setMy_cal_date(String my_cal_date) {
		this.my_cal_date = my_cal_date;
	}
	
	
	public String getMy_cal_time() {
		return my_cal_time;
	}

	public void setMy_cal_time(String my_cal_time) {
		this.my_cal_time = my_cal_time;
	}

	@Override
	public String toString() {
		return "MyCalendarVO [my_cal_index=" + my_cal_index + ", member_id=" + member_id + ", my_write_time="
				+ my_write_time + ", my_cal_contents=" + my_cal_contents + ", my_cal_date=" + my_cal_date
				+ ", my_cal_time=" + my_cal_time + "]";
	}
	
	
	
	
}
