package com.rjcass.commons.semaphore;

public class AndSemaphore extends AbstractIterableSemaphore
{
	public AndSemaphore()
	{
		this(null);
	}

	public AndSemaphore(String name)
	{
		super(name);
	}

	@Override
	protected boolean evaluateState()
	{
		boolean result = true;
		for (Semaphore semaphore : this)
		{
			if (!semaphore.getState())
			{
				result = false;
				break;
			}
		}
		return result;
	}
}
