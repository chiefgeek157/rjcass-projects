package com.rjcass.util;

import java.util.Enumeration;
import java.util.Iterator;

public class IterableEnumeration<T> implements Iterable<T>
{
	private EnumerationIterator<T> mIterator;

	public IterableEnumeration(Enumeration<T> e)
	{
		mIterator = new EnumerationIterator<T>(e);
	}

	public Iterator<T> iterator()
	{
		return mIterator;
	}

	private class EnumerationIterator<U> implements Iterator<U>
	{
		Enumeration<U> mEnum;

		public EnumerationIterator(Enumeration<U> e)
		{
			mEnum = e;
		}

		public boolean hasNext()
		{
			return mEnum.hasMoreElements();
		}

		public U next()
		{
			return mEnum.nextElement();
		}

		public void remove()
		{
			throw new IllegalStateException("Cannot use remove() on an Enumeration");
		}
	}
}
