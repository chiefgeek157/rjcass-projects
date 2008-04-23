package com.rjcass.logging.java;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.rjcass.logging.Log;
import com.rjcass.logging.spi.LogFactory;

public class JavaLoggingLogFactory implements LogFactory
{
	private Level mDebugLevel;
	
	public JavaLoggingLogFactory()
	{}

	@Override
	public Log getLog(String name)
	{
		return new JavaLoggingLog(Logger.getLogger(name),mDebugLevel);
	}

	@Override
	public Log getLog(Class<?> cls)
	{
		return new JavaLoggingLog(Logger.getLogger(cls.getName()),mDebugLevel);
	}

	public void setDebugLevel(Level debugLevel)
	{
		mDebugLevel = debugLevel;
	}
}
