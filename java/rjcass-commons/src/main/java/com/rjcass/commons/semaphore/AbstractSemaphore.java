package com.rjcass.commons.semaphore;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractSemaphore implements Semaphore
{
	private static Log sLog = LogFactory.getLog(AbstractSemaphore.class);

	private boolean mState;
	private String mName;
	private Set<SemaphoreListener> mListeners;

	public AbstractSemaphore()
	{
		this(null);
	}

	public AbstractSemaphore(String name)
	{
		mListeners = new HashSet<SemaphoreListener>();
		setName(name);
	}

	public boolean getState()
	{
		return mState;
	}

	public void setName(String name)
	{
		mName = name;
	}

	public String getName()
	{
		return mName;
	}

	public void addListener(SemaphoreListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(SemaphoreListener listener)
	{
		mListeners.remove(listener);
	}

	protected void setSemaphoreState(boolean state)
	{
		if (state != mState)
		{
			mState = state;
			sLog.debug("Semphore " + mName + " state changed to " + mState);
			fireStateChanged(state);
		}
	}

	private void fireStateChanged(boolean state)
	{
		Set<SemaphoreListener> listeners = new HashSet<SemaphoreListener>(mListeners);
		for (SemaphoreListener listener : listeners)
			listener.semaphoreChanged(this, state);
	}
}
