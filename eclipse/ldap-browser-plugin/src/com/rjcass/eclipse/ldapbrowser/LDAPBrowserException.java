package com.rjcass.eclipse.ldapbrowser;

public class LDAPBrowserException extends RuntimeException
{
	private static final long	serialVersionUID	= -3562425472017801428L;

	public LDAPBrowserException()
	{}

	public LDAPBrowserException(String msg)
	{
		super(msg);
	}

	public LDAPBrowserException(Throwable t)
	{
		super(t);
	}

	public LDAPBrowserException(String msg, Throwable t)
	{
		super(msg, t);
	}
}
