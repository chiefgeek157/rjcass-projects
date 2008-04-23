package com.rjcass.logging.spi;

import com.rjcass.logging.Log;


public interface LogFactory
{
	Log getLog(String name);

	Log getLog(Class<?> cls);
}
