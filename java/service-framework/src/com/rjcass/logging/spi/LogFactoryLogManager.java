package com.rjcass.logging.spi;

import com.rjcass.logging.Log;
import com.rjcass.logging.LogManager;

public class LogFactoryLogManager extends LogManager
{
	private LogFactory mLogFactory;

	public LogFactoryLogManager(LogFactory factory)
	{
		setDelegate(this);
		mLogFactory = factory;
	}

	@Override
	protected Log doGetLogDelegate(String name)
	{
		return mLogFactory.getLog(name);
	}

	@Override
	protected Log doGetLogDelegate(Class<?> cls)
	{
		return mLogFactory.getLog(cls);
	}
}
