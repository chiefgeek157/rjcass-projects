package com.rjcass.depends.builder;

public class BuilderException extends RuntimeException
{
    private static final long serialVersionUID = 2313040908705396679L;

    public BuilderException()
    {
    }

    public BuilderException(String message)
    {
        super(message);
    }

    public BuilderException(Throwable cause)
    {
        super(cause);
    }

    public BuilderException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
