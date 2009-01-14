package com.rjcass.commons.iterator;

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

	public Iterator<String> iterator()
	{
		return this;
	}

	public boolean hasNext()
	{
		return mTokenizer.hasMoreElements();
	}

	public String next()
	{
		return mTokenizer.nextToken();
	}

	public void remove()
	{
		throw new UnsupportedOperationException("remove() not supported");
	}
}