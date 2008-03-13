package com.rjcass.service.boggle;

public class BoggleServiceException extends RuntimeException
{
    private static final long serialVersionUID = -4899866419435423993L;

    public BoggleServiceException()
    {
    }

    public BoggleServiceException(String message)
    {
        super(message);
    }

    public BoggleServiceException(Throwable cause)
    {
        super(cause);
    }

    public BoggleServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
