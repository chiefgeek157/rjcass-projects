package com.rjcass.diff.index;

import com.rjcass.diff.Index;


public class ArrayIndex<T> implements Index<T>
{
	private T[] mArray;

	public ArrayIndex()
	{
	}

	public ArrayIndex(T[] array)
	{
		setArray(array);
	}

	public void setArray(T[] array)
	{
		mArray = array;
	}

	@Override
	public T get(int pos)
	{
		return mArray[pos];
	}

	@Override
	public int size()
	{
		return mArray.length;
	}

	protected T[] getArray()
	{
		return mArray;
	}
}
