package com.rjcass.commons.condition;

public class XorCondition extends AbstractIterableCondition
{
	public XorCondition()
	{}

	public boolean conditionPasses()
	{
		boolean result = false;
		for (Condition condition : this)
		{
			if (condition.conditionPasses())
			{
				if (!result)
				{
					result = true;
				}
				else
				{
					result = false;
					break;
				}
			}
		}
		return result;
	}
}
