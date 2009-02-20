package com.rjcass.commons.semaphore;

public class OrSemaphore extends AbstractIterableSemaphore
{
	public OrSemaphore()
	{
		this(null);
	}

	public OrSemaphore(String name)
	{
		super(name);
	}

	@Override
	protected boolean evaluateState()
	{
		boolean result = false;
		for (Semaphore semaphore : this)
		{
			if (semaphore.getState())
			{
				result = true;
				break;
			}
		}
		return result;
	}
}
