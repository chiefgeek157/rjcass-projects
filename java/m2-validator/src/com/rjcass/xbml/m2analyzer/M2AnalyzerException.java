package com.rjcass.xbml.m2analyzer;

public class M2AnalyzerException extends RuntimeException
{
	private static final long serialVersionUID = 908680610566063124L;

	public M2AnalyzerException()
    {
    }

    public M2AnalyzerException(String message)
    {
        super(message);
    }

    public M2AnalyzerException(Throwable t)
    {
        super(t);
    }

    public M2AnalyzerException(String message, Throwable t)
    {
        super(message, t);
    }
}
