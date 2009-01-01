package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.graph.listener.EventTraceListener;
import com.rjcass.graph.listener.ListenerEvent;
import com.rjcass.graph.managed.ManagedArc;
import com.rjcass.graph.managed.ManagedEntityFactory;
import com.rjcass.graph.managed.ManagedGraph;
import com.rjcass.graph.managed.ManagedNode;

public class BasicManagedEntityFactoryTest
{
	private ManagedEntityFactory mFactory;
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
		mFactory = new BasicManagedEntityFactory();
		mFactory.addListener(mListener);
	}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public void testCreateGraph()
	{
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.MANAGED_ENTITY_FACTORY_GRAPH_CREATED);

		ManagedGraph graph = mFactory.createGraph("graph1");
		assertEquals(BasicGraph.class, graph.getClass());

		assertEquals(events, mListener);
	}

	@Test
	public void testCreateNode()
	{
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.MANAGED_ENTITY_FACTORY_NODE_CREATED);

		ManagedNode node = mFactory.createNode("node1");
		assertEquals(BasicNode.class, node.getClass());

		assertEquals(events, mListener);
	}

	@Test
	public void testCreateArc()
	{
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.MANAGED_ENTITY_FACTORY_ARC_CREATED);

		ManagedArc arc = mFactory.createArc("arc1");
		assertEquals(BasicArc.class, arc.getClass());

		assertEquals(events, mListener);
	}
}
