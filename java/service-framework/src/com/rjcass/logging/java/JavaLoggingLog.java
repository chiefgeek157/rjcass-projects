package com.rjcass.logging.java;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.rjcass.logging.Log;

public class JavaLoggingLog implements Log
{
	private Logger mLogger;
	private Level mDebugLevel = Level.FINE;

	public JavaLoggingLog(Logger log,Level debugLevel)
	{
		mLogger = log;
	}

	@Override
	public void debug(Object obj)
	{
		mLogger.log(mDebugLevel,obj.toString());
	}
}
