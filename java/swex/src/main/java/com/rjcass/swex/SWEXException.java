package com.rjcass.swex;

public class SWEXException extends RuntimeException
{
	private static final long serialVersionUID = -4983609574319138135L;

	public SWEXException()
	{}

	public SWEXException(String message)
	{
		super(message);
	}

	public SWEXException(Throwable cause)
	{
		super(cause);
	}

	public SWEXException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
