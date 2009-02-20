package com.rjcass.commons.semaphore;

public class XorSemaphore extends AbstractIterableSemaphore
{
	public XorSemaphore()
	{
		this(null);
	}

	public XorSemaphore(String name)
	{
		super(name);
	}

	@Override
	protected boolean evaluateState()
	{
		boolean result = false;
		boolean found = false;
		for (Semaphore semaphore : this)
		{
			if (semaphore.getState())
			{
				if (found)
				{
					result = false;
					break;
				}
				else
				{
					result = true;
					found = true;
				}
			}
		}
		return result;
	}
}
