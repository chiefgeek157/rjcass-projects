package com.rjcass.xbml.m2analyzer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

public class ExitAction extends ValidatorAction
{
	private static final long serialVersionUID = -7218581395369581923L;

	public ExitAction(UIServices context)
	{
		super(context);
		putValue(Action.NAME, "Exit");
		putValue(Action.SHORT_DESCRIPTION, "Exit the application");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		getServices().exit();
	}
}
