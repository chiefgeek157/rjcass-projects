package com.rjcass.admgmtchain;

import java.util.ArrayList;
import java.util.List;

public class ADUser
{
	public static String	ATTR_COST_CENTER			= "departmentNumber";
	public static String	ATTR_DEPARTMENT				= "department";
	public static String	ATTR_DISPLAY_NAME			= "displayName";
	public static String	ATTR_DISTINGUISHED_NAME		= "distinguishedName";
	public static String	ATTR_EMAIL					= "mail";
	public static String	ATTR_LAST_LOGON_TIMESTAMP	= "lastLogonTimestamp";
	public static String	ATTR_MANAGER				= "manager";
	public static String	ATTR_PHONE					= "telephoneNumber";
	public static String	ATTR_TITLE					= "title";

	public static String[]	ATTRIBUTES					= { ATTR_COST_CENTER, ATTR_DEPARTMENT, ATTR_DISPLAY_NAME,
			ATTR_DISTINGUISHED_NAME, ATTR_EMAIL, ATTR_LAST_LOGON_TIMESTAMP, ATTR_MANAGER, ATTR_PHONE, ATTR_TITLE };

	private ADUser			mManager;
	private String			mDisplayName;
	private String			mDistinguishedName;
	private String			mEmail;
	private String			mCostCenter;
	private String			mDepartment;
	private String			mPhone;
	private String			mTitle;
	private String			mLastLogonTimestamp;

	public ADUser()
	{}

	public ADUser getManager()
	{
		return mManager;
	}

	public void setManager(ADUser manager)
	{
		mManager = manager;
	}

	public List<ADUser> getManagementChain()
	{
		List<ADUser> chain = new ArrayList<ADUser>();

		chain.add(this);
		ADUser manager = mManager;
		while (manager != null)
		{
			chain.add(manager);
			manager = manager.getManager();
		}
		return chain;
	}

	public String getDisplayName()
	{
		return mDisplayName;
	}

	public void setDisplayName(String displayName)
	{
		mDisplayName = displayName;
	}

	public String getDistinguishedName()
	{
		return mDistinguishedName;
	}

	public void setDistinguishedName(String distinguishedName)
	{
		mDistinguishedName = distinguishedName;
	}

	public String getEmail()
	{
		return mEmail;
	}

	public void setEmail(String email)
	{
		mEmail = email;
	}

	public String getCostCenter()
	{
		return mCostCenter;
	}

	public void setCostCenter(String costCenter)
	{
		mCostCenter = costCenter;
	}

	public String getDepartment()
	{
		return mDepartment;
	}

	public void setDepartment(String department)
	{
		mDepartment = department;
	}

	public String getPhone()
	{
		return mPhone;
	}

	public void setPhone(String phone)
	{
		mPhone = phone;
	}

	public String getTitle()
	{
		return mTitle;
	}

	public void setTitle(String title)
	{
		mTitle = title;
	}

	public int getLevel()
	{
		return getManagementChain().size();
	}

	public String getLastLogonTimestamp()
	{
		return mLastLogonTimestamp;
	}

	public void setLastLogonTimestamp(String lastLogonTimestamp)
	{
		mLastLogonTimestamp = lastLogonTimestamp;
	}

	public String toString()
	{
		StringBuilder b = new StringBuilder("ADUser[");
		b.append("displayName=").append(mDisplayName).append("]");
		return b.toString();
	}
}
