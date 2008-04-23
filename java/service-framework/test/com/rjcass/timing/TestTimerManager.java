package com.rjcass.timing;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rjcass.timing.Timer;
import com.rjcass.timing.TimerManager;

public class TestTimerManager
{
	@BeforeClass
	public static void setUpClass() throws Exception
	{
		new ClassPathXmlApplicationContext(new String[] { "beans.xml" });
	}

	@Test
	public void testGetTimer()
	{
		Timer timer = TimerManager.getTimer("Test");
		assert ("Test" == timer.getName());
	}
}
