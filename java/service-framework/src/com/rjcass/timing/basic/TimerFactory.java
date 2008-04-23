package com.rjcass.timing.basic;

import com.rjcass.timing.Timer;


public interface TimerFactory
{
	Timer createTimer(String name);
}
