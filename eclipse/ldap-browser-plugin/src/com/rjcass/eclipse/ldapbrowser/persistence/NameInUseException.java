package com.rjcass.eclipse.ldapbrowser.persistence;

import com.rjcass.eclipse.ldapbrowser.LDAPBrowserException;

public class NameInUseException extends LDAPBrowserException
{
	private static final long	serialVersionUID	= 39369599332364144L;

	public NameInUseException()
	{}

	public NameInUseException(String msg)
	{
		super(msg);
	}

	public NameInUseException(Throwable t)
	{
		super(t);
	}

	public NameInUseException(String msg, Throwable t)
	{
		super(msg, t);
	}
}
