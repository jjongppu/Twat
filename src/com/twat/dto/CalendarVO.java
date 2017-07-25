package com.twat.dto;

import java.sql.Timestamp;

public class CalendarVO
{
	
	private int cal_num;
	private Timestamp cal_time;
	private String cal_date;
	private int group_id;
	private String cal_memo;
	private String cal_writer;
	private String state_icon;
	private String member_choice;
	private String decide_date;
	private int cal_reference;
	private int cal_depth;
	

	public CalendarVO() {	}
	
	public CalendarVO(int cal_num, Timestamp cal_time, String cal_date, int cal_group, String cal_memo,
			String cal_writer, String state_icon, String member_choice, String decide_date, int cal_reference,
			int cal_depth) {
		super();
		this.cal_num = cal_num;
		this.cal_time = cal_time;
		this.cal_date = cal_date;
		this.group_id = cal_group;
		this.cal_memo = cal_memo;
		this.cal_writer = cal_writer;
		this.state_icon = state_icon;
		this.member_choice = member_choice;
		this.decide_date = decide_date;
		this.cal_reference = cal_reference;
		this.cal_depth = cal_depth;
	}

	// getter & setter ==============================================================================================================


	public int getCal_num() {
		return cal_num;
	}

	public void setCal_num(int cal_num) {
		this.cal_num = cal_num;
	}

	public Timestamp getCal_time() {
		return cal_time;
	}

	public void setCal_time(Timestamp cal_time) {
		this.cal_time = cal_time;
	}

	public String getCal_date() {
		return cal_date;
	}

	public void setCal_date(String cal_date) {
		this.cal_date = cal_date;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public String getCal_memo() {
		return cal_memo;
	}

	public void setCal_memo(String cal_memo) {
		this.cal_memo = cal_memo;
	}

	public String getCal_writer() {
		return cal_writer;
	}

	public void setCal_writer(String cal_writer) {
		this.cal_writer = cal_writer;
	}

	public String getState_icon() {
		return state_icon;
	}

	public void setState_icon(String state_icon) {
		this.state_icon = state_icon;
	}

	public String getMember_choice() {
		return member_choice;
	}

	public void setMember_choice(String member_choice) {
		this.member_choice = member_choice;
	}

	public String getDecide_date() {
		return decide_date;
	}

	public void setDecide_date(String decide_date) {
		this.decide_date = decide_date;
	}

	public int getCal_reference() {
		return cal_reference;
	}

	public void setCal_reference(int cal_reference) {
		this.cal_reference = cal_reference;
	}

	public int getCal_depth() {
		return cal_depth;
	}

	public void setCal_depth(int cal_depth) {
		this.cal_depth = cal_depth;
	}
	
	@Override
	public String toString()
	{
		return "CalendarVO [cal_num=" + cal_num + ", cal_time=" + cal_time + ", cal_date=" + cal_date + ", cal_group="
				+ group_id + ", cal_memo=" + cal_memo + ", cal_writer=" + cal_writer + ", state_icon=" + state_icon
				+ ", member_choice=" + member_choice + ", decide_date=" + decide_date + ", cal_reference="
				+ cal_reference + ", cal_depth=" + cal_depth + "]";
	}
}
