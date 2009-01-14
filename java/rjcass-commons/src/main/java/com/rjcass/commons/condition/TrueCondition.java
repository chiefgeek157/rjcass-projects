package com.rjcass.commons.condition;

public class TrueCondition implements Condition
{
	public TrueCondition()
	{}

	public boolean conditionPasses()
	{
		return true;
	}
}
