package com.rjcass.logging;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rjcass.logging.Log;
import com.rjcass.logging.LogManager;

public class TestLogManager
{
	private static Log sLog = LogManager.getLog("static initialization");
	
	@BeforeClass
	public static void setUpClass() throws Exception
	{
		new ClassPathXmlApplicationContext(new String[] { "beans.xml" });
	}

	@Test
	public void testStaticLog()
	{
		sLog.debug("tests passed");
	}

	@Test
	public void testLogByName()
	{
		Log log = LogManager.getLog("test");
		log.debug("tests passed");
	}

	@Test
	public void testLogByClass()
	{
		Log log = LogManager.getLog(TestLogManager.class);
		log.debug("tests passed");
	}
}
