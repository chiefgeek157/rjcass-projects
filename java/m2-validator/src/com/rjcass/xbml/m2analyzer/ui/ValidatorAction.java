package com.rjcass.xbml.m2analyzer.ui;

import javax.swing.AbstractAction;
import javax.swing.Icon;

public abstract class ValidatorAction extends AbstractAction
{
	private static final long serialVersionUID = -8943443735933639024L;
	
	private UIServices mServices;

	public ValidatorAction(UIServices services)
	{
		super();
		mServices = services;
	}

	public ValidatorAction(String name, Icon icon, UIServices services)
	{
		super(name, icon);
		mServices = services;
	}

	public ValidatorAction(String name, UIServices service)
	{
		super(name);
		mServices = service;
	}

	protected UIServices getServices()
	{
		return mServices;
	}
}
