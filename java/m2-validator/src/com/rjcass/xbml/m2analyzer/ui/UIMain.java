package com.rjcass.xbml.m2analyzer.ui;

import javax.swing.SwingUtilities;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UIMain
{
	public static void main(String[] args)
	{
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]
                { "analyzer-ui.xml" });
        
        final AnalyzerUI ui = (AnalyzerUI)context.getBean("ui");

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				ui.createAndShowGUI();
			}
		});
	}
}
