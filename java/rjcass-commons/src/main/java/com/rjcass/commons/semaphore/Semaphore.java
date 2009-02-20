package com.rjcass.commons.semaphore;

public interface Semaphore
{
	boolean getState();
	void setName(String name);
	String getName();
	void addListener(SemaphoreListener listener);
	void removeListener(SemaphoreListener listener);
}
