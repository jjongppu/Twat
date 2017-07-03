package com.twat.dto;

public class VisitVO {
	private String VISIT_KIND;
	private int VISIT_COUNT;
	
	
	
	public VisitVO(){}
	public VisitVO(String vISIT_KIND, int vISIT_COUNT) {
		super();
		VISIT_KIND = vISIT_KIND;
		VISIT_COUNT = vISIT_COUNT;
	}
	public String getVISIT_KIND() {
		return VISIT_KIND;
	}
	public void setVISIT_KIND(String vISIT_KIND) {
		VISIT_KIND = vISIT_KIND;
	}
	public int getVISIT_COUNT() {
		return VISIT_COUNT;
	}
	public void setVISIT_COUNT(int vISIT_COUNT) {
		VISIT_COUNT = vISIT_COUNT;
	}
	
	
	
}
