package com.rjcass.eclipse.ldapbrowser.model;

import com.rjcass.eclipse.ldapbrowser.LDAPBrowserException;

public class LDAPAuthenticationException extends LDAPBrowserException
{
	private static final long	serialVersionUID	= 7370934132327877203L;

	public LDAPAuthenticationException()
	{}

	public LDAPAuthenticationException(String msg)
	{
		super(msg);
	}

	public LDAPAuthenticationException(Throwable t)
	{
		super(t);
	}

	public LDAPAuthenticationException(String msg, Throwable t)
	{
		super(msg, t);
	}
}
