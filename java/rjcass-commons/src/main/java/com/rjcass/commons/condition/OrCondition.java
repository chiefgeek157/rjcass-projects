package com.rjcass.commons.condition;

public class OrCondition extends AbstractIterableCondition
{
	public OrCondition()
	{}

	public boolean conditionPasses()
	{
		boolean result = false;
		for (Condition condition : this)
		{
			if (condition.conditionPasses())
			{
				result = true;
				break;
			}
		}
		return result;
	}
}
