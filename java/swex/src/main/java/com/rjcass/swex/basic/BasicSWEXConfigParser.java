package com.rjcass.swex.basic;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import com.rjcass.swex.spi.SWEXConfigParser;
import com.rjcass.swex.spi.SWEXConfigParserListener;

public class BasicSWEXConfigParser implements SWEXConfigParser
{
	Set<SWEXConfigParserListener> mListeners;

	public BasicSWEXConfigParser()
	{
		mListeners = new HashSet<SWEXConfigParserListener>();
	}

	public void parse(InputStream is)
	{
		
	}

	public void addListener(SWEXConfigParserListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(SWEXConfigParserListener listener)
	{
		mListeners.remove(listener);
	}
}
