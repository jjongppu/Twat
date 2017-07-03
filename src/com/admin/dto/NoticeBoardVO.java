package com.admin.dto;

public class NoticeBoardVO {
	private int notice_id;
	private String notice_title;
	private String notice_content;
	private String notice_writer;
	private String notice_date;
	private int notice_views;
	private String notice_classification;
	
	public NoticeBoardVO(){}
	
	public NoticeBoardVO(int notice_id, String notice_title, String notice_content, String notice_writer,
			String notice_date, int notice_views, String notice_classification) {
		super();
		this.notice_id = notice_id;
		this.notice_title = notice_title;
		this.notice_content = notice_content;
		this.notice_writer = notice_writer;
		this.notice_date = notice_date;
		this.notice_views = notice_views;
		this.notice_classification = notice_classification;
	}

	public int getNotice_id() {
		return notice_id;
	}

	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}

	public String getNotice_title() {
		return notice_title;
	}

	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}

	public String getNotice_content() {
		return notice_content;
	}

	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}

	public String getNotice_writer() {
		return notice_writer;
	}

	public void setNotice_writer(String notice_writer) {
		this.notice_writer = notice_writer;
	}

	public String getNotice_date() {
		return notice_date;
	}

	public void setNotice_date(String notice_date) {
		this.notice_date = notice_date;
	}

	public int getNotice_views() {
		return notice_views;
	}

	public void setNotice_views(int notice_views) {
		this.notice_views = notice_views;
	}

	public String getNotice_classification() {
		return notice_classification;
	}

	public void setNotice_classification(String notice_classification) {
		this.notice_classification = notice_classification;
	}
	
	
	
	
	

}
