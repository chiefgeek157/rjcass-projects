package com.rjcass.commons.semaphore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractIterableSemaphore extends AbstractSemaphore implements Iterable<Semaphore>, SemaphoreListener
{
	private List<Semaphore> mSemaphores;

	public AbstractIterableSemaphore()
	{
		this(null);
	}

	public AbstractIterableSemaphore(String name)
	{
		super(name);
		mSemaphores = new ArrayList<Semaphore>();
	}

	public void add(Semaphore semaphore)
	{
		if (semaphore == null)
			throw new IllegalArgumentException("Semaphore cannot be null");
		mSemaphores.add(semaphore);
		semaphore.addListener(this);
	}

	public void remove(Semaphore semaphore)
	{
		if (semaphore == null)
			throw new IllegalArgumentException("Semaphore cannot be null");
		mSemaphores.remove(semaphore);
		semaphore.removeListener(this);
	}

	public Iterator<Semaphore> iterator()
	{
		return mSemaphores.iterator();
	}

	public void semaphoreChanged(Semaphore semaphore, boolean state)
	{
		setSemaphoreState(evaluateState());
	}

	protected abstract boolean evaluateState();
}
