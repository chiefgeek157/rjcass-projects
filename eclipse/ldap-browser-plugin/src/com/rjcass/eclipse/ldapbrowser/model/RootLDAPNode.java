package com.rjcass.eclipse.ldapbrowser.model;

import java.util.StringTokenizer;

import com.rjcass.eclipse.ldapbrowser.persistence.ContextInfo;

public class RootLDAPNode extends LDAPNode
{
	private ContextInfo	mInfo;

	public RootLDAPNode(LDAPModel model, ContextInfo info)
	{
		super(model, info.getName());
		mInfo = info;
	}

	public ContextInfo getInfo()
	{
		return mInfo;
	}

	public void setInfo(ContextInfo info)
	{
		mInfo = info;
	}

	public String getProtocol()
	{
		return mInfo.getProtocol();
	}

	public String getHostname()
	{
		return mInfo.getHostname();
	}

	public int getPort()
	{
		return mInfo.getPort();
	}

	public String getPrincipal()
	{
		return mInfo.getPrincipal();
	}

	public String getPassword()
	{
		return mInfo.getPassword();
	}

	public String getAuthMode()
	{
		return mInfo.getAuthMode();
	}

	public String getDN()
	{
		StringTokenizer st = new StringTokenizer(mInfo.getHostname(), ".");
		StringBuilder sb = new StringBuilder();
		while (st.hasMoreTokens())
		{
			sb.append("DC=").append(st.nextToken());
			if (st.hasMoreTokens())
				sb.append(",");
		}
		return sb.toString();
	}
}
