package com.rjcass.logging.commons;

import com.rjcass.logging.Log;

public class CommonsLog implements Log
{
	private org.apache.commons.logging.Log mLog;

	public CommonsLog(org.apache.commons.logging.Log log)
	{
		mLog = log;
	}

	@Override
	public void debug(Object obj)
	{
		mLog.debug(obj);
	}
}
