package com.rjcass.graph.basic;

public class GraphException extends RuntimeException
{
    private static final long serialVersionUID = -4113754503045166149L;

    public GraphException()
    {
        super();
    }

    public GraphException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }

    public GraphException(String arg0)
    {
        super(arg0);
    }

    public GraphException(Throwable arg0)
    {
        super(arg0);
    }
}
