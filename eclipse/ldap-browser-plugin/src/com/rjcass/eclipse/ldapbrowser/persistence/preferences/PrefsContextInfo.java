package com.rjcass.eclipse.ldapbrowser.persistence.preferences;

import com.rjcass.eclipse.ldapbrowser.persistence.ContextInfo;

public class PrefsContextInfo implements ContextInfo
{
	private String	mName;
	private String	mProtocol;
	private String	mHostname;
	private int		mPort;
	private String	mAuthMode;
	private String	mPrincipal;
	private String	mPassword;

	public PrefsContextInfo(String name, String protocol, String hostname, int port, String authMode, String principal,
			String password)
	{
		setName(name);
		setProtocol(protocol);
		setHostname(hostname);
		setPort(port);
		setAuthMode(authMode);
		setPrincipal(principal);
		setPassword(password);
	}

	public String getName()
	{
		return mName;
	}

	public void setName(String name)
	{
		mName = name;
	}

	public String getProtocol()
	{
		return mProtocol;
	}

	public void setProtocol(String protocol)
	{
		mProtocol = protocol;
	}

	public String getHostname()
	{
		return mHostname;
	}

	public void setHostname(String hostname)
	{
		mHostname = hostname;
	}

	public int getPort()
	{
		return mPort;
	}

	public void setPort(int port)
	{
		mPort = port;
	}

	public String getAuthMode()
	{
		return mAuthMode;
	}

	public void setAuthMode(String authMode)
	{
		mAuthMode = authMode;
	}

	public String getPrincipal()
	{
		return mPrincipal;
	}

	public void setPrincipal(String principal)
	{
		mPrincipal = principal;
	}

	public String getPassword()
	{
		return mPassword;
	}

	public void setPassword(String password)
	{
		mPassword = password;
	}

}
