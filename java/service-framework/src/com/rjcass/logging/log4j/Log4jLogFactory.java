package com.rjcass.logging.log4j;

import org.apache.log4j.Logger;

import com.rjcass.logging.Log;
import com.rjcass.logging.spi.LogFactory;

public class Log4jLogFactory implements LogFactory
{
	public Log4jLogFactory()
	{}

	@Override
	public Log getLog(String name)
	{
		return new Log4jLog(Logger.getLogger(name));
	}

	@Override
	public Log getLog(Class<?> cls)
	{
		return new Log4jLog(Logger.getLogger(cls));
	}
}
