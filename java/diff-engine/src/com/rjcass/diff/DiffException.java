package com.rjcass.diff;

public class DiffException extends RuntimeException
{
	private static final long serialVersionUID = 8785487364952178508L;

	public DiffException()
	{}

	public DiffException(String message)
	{
		super(message);
	}

	public DiffException(Throwable cause)
	{
		super(cause);
	}

	public DiffException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
