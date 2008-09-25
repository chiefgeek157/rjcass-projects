package com.rjcass.xbml.m2analyzer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class OpenAction extends ValidatorAction
{
	private static final long serialVersionUID = 3989837245651821822L;

	public OpenAction(UIServices context)
	{
		super(context);
		putValue(Action.NAME, "Open");
		putValue(Action.SHORT_DESCRIPTION, "Open a file or folder for procecssing");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Folders and Zip files (*.zip)", "zip");
		chooser.setFileFilter(filter);
		int choice = chooser.showOpenDialog(getServices().getMainFrame());
		if (choice == JFileChooser.APPROVE_OPTION)
		{
			getServices().open(chooser.getSelectedFile());
		}
	}
}
