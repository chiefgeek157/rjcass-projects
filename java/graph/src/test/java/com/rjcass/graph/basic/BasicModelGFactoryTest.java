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

import com.rjcass.graph.GModel;
import com.rjcass.graph.listener.EventTraceListener;
import com.rjcass.graph.listener.ListenerEventType;
import com.rjcass.graph.managed.ManagedGModel;
import com.rjcass.graph.managed.basic.BasicManagedGModelFactory;
import com.rjcass.graph.managed.basic.BasicGModel;

public class BasicModelGFactoryTest extends BasicTestBase
{
	private static final Logger sLog = Logger.getLogger(BasicModelGFactoryTest.class);

	private BasicManagedGModelFactory mFactory;
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
		mFactory = new BasicManagedGModelFactory();
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

		GModel model = mFactory.createModel();
		assertNotNull(model);
		assertEquals(BasicGModel.class, model.getClass());

		dump("testCreateModel", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testCreateManagedModel()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.MODEL_FACTORY_MODEL_CREATED, "model1");

		ManagedGModel model = mFactory.createManagedModel();
		assertNotNull(model);
		assertEquals(BasicGModel.class, model.getClass());

		dump("testCreateManagedModel", mListener, sLog);
		assertEquals(events, mListener);
	}
}
