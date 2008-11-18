package com.rjcass.diff.index;

import java.util.List;

import com.rjcass.diff.Index;

public class ListIndex<T> implements Index<T>
{
	private List<T> mList;

	public ListIndex(List<T> list)
	{
		mList = list;
	}

	@Override
	public T get(int index)
	{
		return mList.get(index);
	}

	@Override
	public int size()
	{
		return mList.size();
	}
	
	protected List<T> getList()
	{
		return mList;
	}
}
