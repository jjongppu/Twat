package com.twat.dto;


public class CalgatherVO {
	private int group_id;
	private String group_name;
	private String create_date;
	private String group_master;
	private String group_master_name;
	private String group_img;
	private int group_count;
	
	
	public CalgatherVO(){}
	
	public CalgatherVO(int group_id, String group_name, String create_date, String group_master,
			String group_master_name, String group_img, int group_count) {
		this.group_id = group_id;
		this.group_name = group_name;
		this.create_date = create_date;
		this.group_master = group_master;
		this.group_master_name = group_master_name;
		this.group_img = group_img;
		this.group_count = group_count;
	}
	public int getGroup_count() {
		return group_count;
	}
	
	public void setGroup_count(int group_count) {
		this.group_count = group_count;
	}
	
	public int getGroup_id() {
		return group_id;
	}
	
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getGroup_master() {
		return group_master;
	}
	public void setGroup_master(String group_master) {
		this.group_master = group_master;
	}
	public String getGroup_master_name() {
		return group_master_name;
	}
	public void setGroup_master_name(String group_master_name) {
		this.group_master_name = group_master_name;
	}
	public String getGroup_img() {
		return group_img;
	}
	public void setGroup_img(String group_img) {
		this.group_img = group_img;
	}
	

}
