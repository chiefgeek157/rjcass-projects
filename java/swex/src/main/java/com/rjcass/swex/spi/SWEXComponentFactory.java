package com.rjcass.swex.spi;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public interface SWEXComponentFactory
{
	JFrame createJFrame();
	JMenuBar createJMenuBar();
	JMenu createJMenu();
}
