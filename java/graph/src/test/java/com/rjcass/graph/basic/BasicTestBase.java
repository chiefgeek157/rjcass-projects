package com.rjcass.graph.basic;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.rjcass.graph.listener.EventTraceListener;
import com.rjcass.graph.listener.ListenerEvent;

public class BasicTestBase
{
	private static ConsoleAppender sAppender;

	@BeforeClass
	public static void setUpBeforeClassBase() throws Exception
	{
		if (sAppender == null)
		{
			PatternLayout layout = new PatternLayout("%r: %m\n");
			sAppender = new ConsoleAppender(layout, ConsoleAppender.SYSTEM_OUT);
		}
	}

	@AfterClass
	public static void tearDownAfterClassBase() throws Exception
	{}

	@Before
	public void setUpBase() throws Exception
	{
		Logger rootLogger = Logger.getRootLogger();
		if (!rootLogger.isAttached(sAppender))
			rootLogger.addAppender(sAppender);
		rootLogger.setLevel(Level.WARN);
	}

	@After
	public void tearDownBase() throws Exception
	{}

	protected void dump(String header, EventTraceListener listener, Logger log)
	{
		log.debug(header);
		for (ListenerEvent event : listener.getEvents())
			log.debug(event);
	}
}
