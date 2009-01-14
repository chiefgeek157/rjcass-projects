package com.rjcass.commons.condition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractIterableCondition implements Condition, Iterable<Condition>
{
	private List<Condition> mConditions;

	public AbstractIterableCondition()
	{
		mConditions = new ArrayList<Condition>();
	}

	public void addCondition(Condition condition)
	{
		mConditions.add(condition);
	}

	public void removeCondition(Condition condition)
	{
		mConditions.remove(condition);
	}

	public Iterator<Condition> iterator()
	{
		return mConditions.iterator();
	}
}
