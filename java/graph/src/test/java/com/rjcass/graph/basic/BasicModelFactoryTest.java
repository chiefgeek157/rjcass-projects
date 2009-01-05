package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.graph.Model;
import com.rjcass.graph.listener.EventTraceListener;
import com.rjcass.graph.listener.ListenerEventType;
import com.rjcass.graph.managed.ManagedModel;
import com.rjcass.graph.managed.basic.BasicManagedModelFactory;
import com.rjcass.graph.managed.basic.BasicModel;

public class BasicModelFactoryTest extends BasicTestBase
{
	private static final Logger sLog = Logger.getLogger(BasicModelFactoryTest.class);

	private BasicManagedModelFactory mFactory;
	private EventTraceListener mListener;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{}

	@Before
	public void setUp() throws Exception
	{
		mListener = new EventTraceListener();
		mFactory = new BasicManagedModelFactory();
		mFactory.addModelFactoryListener(mListener);
	}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public void testCreateModel()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.MODEL_FACTORY_MODEL_CREATED, "model1");

		Model model = mFactory.createModel();
		assertNotNull(model);
		assertEquals(BasicModel.class, model.getClass());

		dump("testCreateModel", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testCreateManagedModel()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.MODEL_FACTORY_MODEL_CREATED, "model1");

		ManagedModel model = mFactory.createManagedModel();
		assertNotNull(model);
		assertEquals(BasicModel.class, model.getClass());

		dump("testCreateManagedModel", mListener, sLog);
		assertEquals(events, mListener);
	}
}
