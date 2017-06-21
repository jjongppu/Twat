package com.twat.dto;

public class QnaVO {
	private String QNA_ID;
	private String MEMBER_ID;
	private String QNA_TITLE;
	private String QNA_DATE;
	private String QNA_CONTENTS;
	private String QNA_REPLY;
	
	public QnaVO() {}
	

	public QnaVO(String qNA_ID, String mEMBER_ID, String qNA_TITLE, String qNA_DATE, String qNA_CONTENTS,
			String qNA_REPLY) {
		super();
		QNA_ID = qNA_ID;
		MEMBER_ID = mEMBER_ID;
		QNA_TITLE = qNA_TITLE;
		QNA_DATE = qNA_DATE;
		QNA_CONTENTS = qNA_CONTENTS;
		QNA_REPLY = qNA_REPLY;
	}


	public String getQNA_ID() {
		return QNA_ID;
	}


	public void setQNA_ID(String qNA_ID) {
		QNA_ID = qNA_ID;
	}


	public String getMEMBER_ID() {
		return MEMBER_ID;
	}


	public void setMEMBER_ID(String mEMBER_ID) {
		MEMBER_ID = mEMBER_ID;
	}


	public String getQNA_TITLE() {
		return QNA_TITLE;
	}


	public void setQNA_TITLE(String qNA_TITLE) {
		QNA_TITLE = qNA_TITLE;
	}


	public String getQNA_DATE() {
		return QNA_DATE;
	}


	public void setQNA_DATE(String qNA_DATE) {
		QNA_DATE = qNA_DATE;
	}


	public String getQNA_CONTENTS() {
		return QNA_CONTENTS;
	}


	public void setQNA_CONTENTS(String qNA_CONTENTS) {
		QNA_CONTENTS = qNA_CONTENTS;
	}


	public String getQNA_REPLY() {
		return QNA_REPLY;
	}


	public void setQNA_REPLY(String qNA_REPLY) {
		QNA_REPLY = qNA_REPLY;
	}


	@Override
	public String toString() {
		return "QnaDAO [QNA_ID=" + QNA_ID + ", MEMBER_ID=" + MEMBER_ID + ", QNA_TITLE=" + QNA_TITLE + ", QNA_DATE="
				+ QNA_DATE + ", QNA_CONTENTS=" + QNA_CONTENTS + ", QNA_REPLY=" + QNA_REPLY + "]";
	}
	
	
	
	
}
