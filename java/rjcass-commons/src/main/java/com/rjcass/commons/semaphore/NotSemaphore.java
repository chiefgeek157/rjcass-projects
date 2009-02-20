package com.rjcass.commons.semaphore;

public class NotSemaphore extends AbstractUnarySemaphore
{
	public NotSemaphore()
	{
		this(null);
	}

	public NotSemaphore(String name)
	{
		super(name);
	}

	@Override
	protected boolean evaluateState()
	{
		return (!getSemaphore().getState());
	}
}
