package com.rjcass.graph;

public class GGraphException extends RuntimeException
{
    private static final long serialVersionUID = -4113754503045166149L;

    public GGraphException()
    {
        super();
    }

    public GGraphException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }

    public GGraphException(String arg0)
    {
        super(arg0);
    }

    public GGraphException(Throwable arg0)
    {
        super(arg0);
    }
}
