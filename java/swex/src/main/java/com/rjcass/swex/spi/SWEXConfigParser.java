package com.rjcass.swex.spi;

import java.io.InputStream;

public interface SWEXConfigParser
{
	void parse(InputStream is);
	void addListener(SWEXConfigParserListener listener);
	void removeListener(SWEXConfigParserListener listener);
}
