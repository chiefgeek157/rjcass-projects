package com.rjcass.commons.semaphore;

public abstract class AbstractUnarySemaphore extends AbstractSemaphore implements SemaphoreListener
{
	private Semaphore mSemaphore;

	public AbstractUnarySemaphore()
	{
		this(null);
	}

	public AbstractUnarySemaphore(String name)
	{
		super(name);
	}

	public void setSemaphore(Semaphore semaphore)
	{
		if (mSemaphore != null)
			mSemaphore.removeListener(this);

		mSemaphore = semaphore;

		if (mSemaphore != null)
			mSemaphore.addListener(this);
	}

	public Semaphore getSemaphore()
	{
		return mSemaphore;
	}

	public void semaphoreChanged(Semaphore semaphore, boolean state)
	{
		setSemaphoreState(evaluateState());
	}

	protected abstract boolean evaluateState();
}
