package com.rjcass.util;


public class ClassPathUtil
{
    public static Iterable<String> getPaths(String path)
    {
        return new StringTokenIterator(path, System
                .getProperty("path.separator"));
    }

    private ClassPathUtil()
    {
    }
}
