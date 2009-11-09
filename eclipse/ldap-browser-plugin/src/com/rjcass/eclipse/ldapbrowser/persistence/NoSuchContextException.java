package com.rjcass.eclipse.ldapbrowser.persistence;

import com.rjcass.eclipse.ldapbrowser.LDAPBrowserException;

public class NoSuchContextException extends LDAPBrowserException
{
	private static final long	serialVersionUID	= -1943799740917114504L;

	public NoSuchContextException()
	{}

	public NoSuchContextException(String msg)
	{
		super(msg);
	}

	public NoSuchContextException(Throwable t)
	{
		super(t);
	}

	public NoSuchContextException(String msg, Throwable t)
	{
		super(msg, t);
	}
}
