package com.rjcass.commons.condition;

public class FalseCondition implements Condition
{
	public FalseCondition()
	{}

	public boolean conditionPasses()
	{
		return false;
	}
}
