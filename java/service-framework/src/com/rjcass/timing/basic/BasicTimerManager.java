package com.rjcass.timing.basic;

import com.rjcass.timing.Timer;
import com.rjcass.timing.TimerManager;

public class BasicTimerManager extends TimerManager
{
	private TimerFactory mTimerFactory;

	public BasicTimerManager(TimerFactory factory)
	{
		mTimerFactory = factory;
	}

	protected Timer doGetTimer(String name)
	{
		return mTimerFactory.createTimer(name);
	}
}
