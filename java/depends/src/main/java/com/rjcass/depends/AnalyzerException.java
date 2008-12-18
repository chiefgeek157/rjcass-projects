package com.rjcass.depends;

public class AnalyzerException extends RuntimeException
{
    private static final long serialVersionUID = 2313040908705396679L;

    public AnalyzerException()
    {
    }

    public AnalyzerException(String message)
    {
        super(message);
    }

    public AnalyzerException(Throwable cause)
    {
        super(cause);
    }

    public AnalyzerException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
