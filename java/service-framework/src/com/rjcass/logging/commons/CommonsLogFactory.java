package com.rjcass.logging.commons;

import com.rjcass.logging.Log;
import com.rjcass.logging.spi.LogFactory;

public class CommonsLogFactory implements LogFactory
{
	public CommonsLogFactory()
	{}

	@Override
	public Log getLog(String name)
	{
		return new CommonsLog(org.apache.commons.logging.LogFactory
				.getLog(name));
	}

	@Override
	public Log getLog(Class<?> cls)
	{
		return new CommonsLog(org.apache.commons.logging.LogFactory.getLog(cls));
	}
}
