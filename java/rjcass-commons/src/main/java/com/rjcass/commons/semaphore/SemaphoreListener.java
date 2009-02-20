package com.rjcass.commons.semaphore;

public interface SemaphoreListener
{
	void semaphoreChanged(Semaphore semaphore, boolean state);
}
