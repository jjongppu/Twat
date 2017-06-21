package com.twat.dto;

import java.sql.Timestamp;

public class QnaVO {
	private int QNA_ID;
	private String MEMBER_ID;
	private String QNA_CATEGORY;
	private int QNA_PW;
	private String QNA_TITLE;
	private String QNA_CONTENTS;
	private Timestamp QNA_DATE;
	private String QNA_REPLY;
	
	public QnaVO() {}

	public QnaVO(int qNA_ID, String mEMBER_ID, String qNA_CATEGORY, int qNA_PW, String qNA_TITLE, String qNA_CONTENTS,
			Timestamp qNA_DATE, String qNA_REPLY) {
		super();
		QNA_ID = qNA_ID;
		MEMBER_ID = mEMBER_ID;
		QNA_CATEGORY = qNA_CATEGORY;
		QNA_PW = qNA_PW;
		QNA_TITLE = qNA_TITLE;
		QNA_CONTENTS = qNA_CONTENTS;
		QNA_DATE = qNA_DATE;
		QNA_REPLY = qNA_REPLY;
	}

	public int getQNA_ID() {
		return QNA_ID;
	}

	public void setQNA_ID(int qNA_ID) {
		QNA_ID = qNA_ID;
	}

	public String getMEMBER_ID() {
		return MEMBER_ID;
	}

	public void setMEMBER_ID(String mEMBER_ID) {
		MEMBER_ID = mEMBER_ID;
	}

	public String getQNA_CATEGORY() {
		return QNA_CATEGORY;
	}

	public void setQNA_CATEGORY(String qNA_CATEGORY) {
		QNA_CATEGORY = qNA_CATEGORY;
	}


	public void setQNA_PW(int qNA_PW) {
		QNA_PW = qNA_PW;
	}

	public String getQNA_TITLE() {
		return QNA_TITLE;
	}

	public void setQNA_TITLE(String qNA_TITLE) {
		QNA_TITLE = qNA_TITLE;
	}

	public String getQNA_CONTENTS() {
		return QNA_CONTENTS;
	}

	public void setQNA_CONTENTS(String qNA_CONTENTS) {
		QNA_CONTENTS = qNA_CONTENTS;
	}

	public Timestamp getQNA_DATE() {
		return QNA_DATE;
	}

	public void setQNA_DATE(Timestamp qNA_DATE) {
		QNA_DATE = qNA_DATE;
	}

	public String getQNA_REPLY() {
		return QNA_REPLY;
	}

	public void setQNA_REPLY(String qNA_REPLY) {
		QNA_REPLY = qNA_REPLY;
	}

	@Override
	public String toString() {
		return "QnaVO [QNA_ID=" + QNA_ID + ", MEMBER_ID=" + MEMBER_ID + ", QNA_CATEGORY=" + QNA_CATEGORY
				+ ", QNA_PW=" + QNA_PW + ", QNA_TITLE=" + QNA_TITLE + ", QNA_CONTENTS=" + QNA_CONTENTS
				+ ", QNA_DATE=" + QNA_DATE + ", QNA_REPLY=" + QNA_REPLY + "]";
	}
	
	
}
