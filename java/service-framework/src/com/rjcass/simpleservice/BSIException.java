package com.rjcass.simpleservice;

public class BSIException extends RuntimeException
{
	private static final long serialVersionUID = -3281840284849833610L;

	public BSIException()
	{}

	public BSIException(String message)
	{
		super(message);
	}

	public BSIException(Throwable cause)
	{
		super(cause);
	}

	public BSIException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
