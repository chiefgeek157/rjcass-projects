package com.rjcass.commons.condition;

public class NotCondition extends AbstractUnaryCondition
{
	public NotCondition()
	{}

	public boolean conditionPasses()
	{
		return (!getCondition().conditionPasses());
	}
}
