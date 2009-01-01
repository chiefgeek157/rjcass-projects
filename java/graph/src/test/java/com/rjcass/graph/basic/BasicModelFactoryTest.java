package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.graph.Model;
import com.rjcass.graph.listener.EventTraceListener;
import com.rjcass.graph.listener.ListenerEvent;
import com.rjcass.graph.managed.ManagedModel;

public class BasicModelFactoryTest
{
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
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.MODEL_FACTORY_MODEL_CREATED);

		Model model = mFactory.createModel("model1");
		assertNotNull(model);
		assertEquals(BasicModel.class, model.getClass());

		assertEquals(events, mListener);
	}

	@Test
	public void testCreateManagedModel()
	{
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.MODEL_FACTORY_MODEL_CREATED);

		ManagedModel model = mFactory.createManagedModel("model1");
		assertNotNull(model);
		assertEquals(BasicModel.class, model.getClass());

		assertEquals(events, mListener);
	}
}
