package com.admin.dto;

public class AdminVO {
	private String admin_id;
	private String admin_pw;
	private String admin_grade;
	
	public AdminVO(){}
	public AdminVO(String admin_id, String admin_pw, String admin_grade) {
		super();
		this.admin_id = admin_id;
		this.admin_pw = admin_pw;
		this.admin_grade = admin_grade;
	}
	
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public String getAdmin_pw() {
		return admin_pw;
	}
	public void setAdmin_pw(String admin_pw) {
		this.admin_pw = admin_pw;
	}
	public String getAdmin_grade() {
		return admin_grade;
	}
	public void setAdmin_grade(String admin_grade) {
		this.admin_grade = admin_grade;
	}
	
	
	@Override
	public String toString() {
		return "AdminVO [admin_id=" + admin_id + ", admin_pw=" + admin_pw + ", admin_grade=" + admin_grade + "]";
	}
	
	
	
}
