package com.rjcass.util;

import java.util.Iterator;
import java.util.StringTokenizer;

public class StringTokenIterator implements Iterable<String>, Iterator<String>
{
    private StringTokenizer mTokenizer;

    public StringTokenIterator(String s)
    {
        mTokenizer = new StringTokenizer(s);
    }

    public StringTokenIterator(String s, String delims)
    {
        mTokenizer = new StringTokenizer(s, delims);
    }

    public StringTokenIterator(String s, String delims, boolean returnDelims)
    {
        mTokenizer = new StringTokenizer(s, delims, returnDelims);
    }

    @Override
    public Iterator<String> iterator()
    {
        return this;
    }

    @Override
    public boolean hasNext()
    {
        return mTokenizer.hasMoreElements();
    }

    @Override
    public String next()
    {
        return mTokenizer.nextToken();
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("remove() not supported");
    }
}