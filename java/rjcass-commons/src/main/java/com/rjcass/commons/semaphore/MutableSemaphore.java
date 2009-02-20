package com.rjcass.commons.semaphore;

public class MutableSemaphore extends AbstractSemaphore
{
	public MutableSemaphore()
	{
		this(null, false);
	}

	public MutableSemaphore(boolean state)
	{
		this(null, state);
	}

	public MutableSemaphore(String name)
	{
		this(name, false);
	}

	public MutableSemaphore(String name, boolean state)
	{
		super(name);
		setState(state);
	}

	public void setState(boolean state)
	{
		setSemaphoreState(state);
	}
}
