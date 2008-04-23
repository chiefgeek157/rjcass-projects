package com.rjcass.timing;

public interface Timer
{
	String getName();

	void start();

	void stop();

	long getElapsedTime();

	long getStartTime();

	long getStopTime();

	void reset();
}
