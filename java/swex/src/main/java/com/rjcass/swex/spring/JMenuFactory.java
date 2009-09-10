package com.rjcass.swex.spring;

import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class JMenuFactory
{
	private List<JMenuItem> mMenuItems;
	
	public JMenuFactory()
	{}

	public JMenu createMenu()
	{
		return new JMenu();
	}
	
	public void setMenuItems(List<JMenuItem> menuItems)
	{
		mMenuItems = menuItems;
	}
}
