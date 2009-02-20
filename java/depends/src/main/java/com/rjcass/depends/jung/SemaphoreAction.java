package com.rjcass.depends.jung;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import com.rjcass.commons.semaphore.Semaphore;
import com.rjcass.commons.semaphore.SemaphoreListener;

public abstract class SemaphoreAction extends AbstractAction implements SemaphoreListener
{
	private static final long serialVersionUID = 5825509947962086496L;

	private Semaphore mSemaphore;

	public SemaphoreAction()
	{}

	public SemaphoreAction(String name)
	{
		super(name);
	}

	public SemaphoreAction(String name, Icon icon)
	{
		super(name, icon);
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
		setEnabled(state);
	}
}
