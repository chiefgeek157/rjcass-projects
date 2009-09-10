package com.rjcass.swex.basic;

import java.io.IOException;

import javax.swing.JFrame;

import org.springframework.core.io.Resource;

import com.rjcass.swex.SWEXBuilder;
import com.rjcass.swex.SWEXException;
import com.rjcass.swex.spi.SWEXComponentFactory;
import com.rjcass.swex.spi.SWEXConfigParser;
import com.rjcass.swex.spi.SWEXConfigParserListener;

public class BasicSWEXBuilder implements SWEXBuilder, SWEXConfigParserListener
{
	private Resource mConfigResource;
	private SWEXComponentFactory mFactory = new BasicSWEXComponentFactory();
	private SWEXConfigParser mParser = new BasicSWEXConfigParser();

	public JFrame build()
	{
		JFrame rootFrame = mFactory.createJFrame();
		build(rootFrame);
		return rootFrame;
	}

	public void build(JFrame rootFrame)
	{
		try
		{
			mParser.parse(mConfigResource.getInputStream());
		}
		catch (IOException e)
		{
			throw new SWEXException(e);
		}
	}
}
