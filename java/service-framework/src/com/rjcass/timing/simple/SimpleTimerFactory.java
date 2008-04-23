package com.rjcass.timing.simple;

import com.rjcass.timing.Timer;
import com.rjcass.timing.basic.TimerFactory;

public class SimpleTimerFactory implements TimerFactory
{
	public SimpleTimerFactory()
	{}

	@Override
	public Timer createTimer(String name)
	{
		return new SimpleTimer(name);
	}

}
