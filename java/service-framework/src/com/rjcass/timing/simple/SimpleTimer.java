package com.rjcass.timing.simple;

import com.rjcass.timing.Timer;

public class SimpleTimer implements Timer
{
	private String mName;
	private boolean mRunning;
	private long mElapsedTime;
	private long mStartTime;
	private long mStopTime;

	public SimpleTimer(String name)
	{
		mName = name;
	}

	@Override
	public String getName()
	{
		return mName;
	}

	@Override
	synchronized public void start()
	{
		if (!mRunning)
		{
			mStartTime = System.currentTimeMillis();
			mRunning = true;
		}
	}

	@Override
	synchronized public void stop()
	{
		if (mRunning)
		{
			mStopTime = System.currentTimeMillis();
			mElapsedTime += mStopTime - mStartTime;
			mRunning = false;
		}
	}

	@Override
	synchronized public long getElapsedTime()
	{
		long time = mElapsedTime;
		if (mRunning) time += System.currentTimeMillis() - mStartTime;
		return time;
	}

	@Override
	synchronized public long getStartTime()
	{
		return mStartTime;
	}

	@Override
	synchronized public long getStopTime()
	{
		long time = mStopTime;
		if (mRunning) time = System.currentTimeMillis();
		return time;
	}

	@Override
	synchronized public void reset()
	{
		if (!mRunning) mElapsedTime = 0;
	}
}
