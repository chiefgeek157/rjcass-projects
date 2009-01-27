package com.rjcass.depends;

public class DependsException extends RuntimeException
{
	private static final long serialVersionUID = 745622566953780581L;

	public DependsException()
	{}

	public DependsException(String message)
	{
		super(message);
	}

	public DependsException(Throwable cause)
	{
		super(cause);
	}

	public DependsException(String message, Throwable cause)
	{
		super(message, cause);
	}
}