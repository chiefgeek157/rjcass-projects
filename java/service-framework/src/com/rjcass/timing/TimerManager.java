package com.rjcass.timing;

public abstract class TimerManager
{
	private static TimerManager sDelegate;

	public static Timer getTimer(String name)
	{
		if (sDelegate == null)
			throw new IllegalStateException("Delegate has not been set");
		return sDelegate.doGetTimer(name);
	}

	public TimerManager()
	{}

	protected final void setDelegate()
	{
		if (sDelegate != null)
			throw new IllegalStateException("Attempt to create second instance");
		sDelegate = this;
	}

	protected abstract Timer doGetTimer(String name);
}
