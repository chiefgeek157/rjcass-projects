package com.rjcass.commons.condition;

public abstract class AbstractUnaryCondition implements Condition
{
	private Condition mCondition;

	public AbstractUnaryCondition()
	{}

	public void setCondition(Condition condition)
	{
		mCondition = condition;
	}

	public Condition getCondition()
	{
		return mCondition;
	}
}
