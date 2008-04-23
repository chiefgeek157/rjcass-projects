package com.rjcass.service;

public class BSIException extends RuntimeException
{
	private static final long serialVersionUID = 1131033089933356677L;

	public BSIException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public BSIException(String message)
	{
		super(message);
	}

	public BSIException(Throwable cause)
	{
		super(cause);
	}

	public BSIException()
	{
	}
}
