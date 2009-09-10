package com.rjcass.swex.basic;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.rjcass.swex.SWEXException;
import com.rjcass.swex.spi.SWEXComponentFactory;

public class BasicSWEXComponentFactory implements SWEXComponentFactory
{
	private Class<JFrame> mJFrameClass = JFrame.class;
	private Class<JMenu> mJMenuClass = JMenu.class;
	private Class<JMenuBar> mJMenuBarClass = JMenuBar.class;

	public JFrame createJFrame()
	{
		JFrame result = null;
		try
		{
			result = mJFrameClass.newInstance();
		}
		catch (InstantiationException e)
		{
			throw new SWEXException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new SWEXException(e);
		}
		return result;
	}

	public JMenu createJMenu()
	{
		JMenu result = null;
		try
		{
			result = mJMenuClass.newInstance();
		}
		catch (InstantiationException e)
		{
			throw new SWEXException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new SWEXException(e);
		}
		return result;
	}

	public JMenuBar createJMenuBar()
	{
		JMenuBar result = null;
		try
		{
			result = mJMenuBarClass.newInstance();
		}
		catch (InstantiationException e)
		{
			throw new SWEXException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new SWEXException(e);
		}
		return result;
	}
}
