package com.rjcass.logging.log4j;

import org.apache.log4j.Logger;

import com.rjcass.logging.Log;

public class Log4jLog implements Log
{
	private Logger mLogger;

	public Log4jLog(Logger log)
	{
		mLogger = log;
	}

	@Override
	public void debug(Object obj)
	{
		mLogger.debug(obj);
	}
}
