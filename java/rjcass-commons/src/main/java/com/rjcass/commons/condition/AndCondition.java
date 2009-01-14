package com.rjcass.commons.condition;

public class AndCondition extends AbstractIterableCondition
{
	public AndCondition()
	{}

	public boolean conditionPasses()
	{
		boolean result = true;
		for (Condition condition : this)
		{
			if (!condition.conditionPasses())
			{
				result = false;
				break;
			}
		}
		return result;
	}
}
