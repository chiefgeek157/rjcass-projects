package com.rjcass.hashiokakero;

public class HashiException extends RuntimeException
{
    private static final long serialVersionUID = -8141654899464314857L;

    public HashiException()
    {
    }

    public HashiException(String arg0)
    {
        super(arg0);
    }

    public HashiException(Throwable arg0)
    {
        super(arg0);
    }

    public HashiException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }
}
