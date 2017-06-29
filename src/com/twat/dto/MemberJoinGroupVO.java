package com.twat.dto;

public class MemberJoinGroupVO
{
	private String MEMBER_ID;
	private String GROUP_ID;
	private int CALENDAR_VIEW;
	private int CALENDAR_READ_VIEW;
	
	public MemberJoinGroupVO() { }
	
	public MemberJoinGroupVO(String MEMBER_ID, String GROUP_ID)
	{
		this.MEMBER_ID = MEMBER_ID;
		this.GROUP_ID = GROUP_ID;
		this.CALENDAR_VIEW = CALENDAR_VIEW;
	}
	
	public int getCALENDAR_READ_VIEW()
	{
		return CALENDAR_VIEW;
	}

	public void setCALENDAR_READ_VIEW(int CALENDAR_READ_VIEW)
	{
		this.CALENDAR_VIEW = CALENDAR_VIEW;
	}
	
	public int getCALENDAR_VIEW()
	{
		return CALENDAR_VIEW;
	}

	public void setCALENDAR_VIEW(int CALENDAR_VIEW)
	{
		this.CALENDAR_VIEW = CALENDAR_VIEW;
	}

	public String getMEMBER_ID()
	{
		return MEMBER_ID;
	}

	public void setMEMBER_ID(String MEMBER_ID)
	{
		this.MEMBER_ID = MEMBER_ID;
	}

	public String getGROUP_ID()
	{
		return GROUP_ID;
	}

	public void setGROUP_ID(String GROUP_ID)
	{
		this.GROUP_ID = GROUP_ID;
	}

	@Override
	public String toString()
	{
		return "MemberJoinGroup [MEMBER_ID=" + MEMBER_ID + ", GROUP_ID=" + GROUP_ID + "]";
	}
}
