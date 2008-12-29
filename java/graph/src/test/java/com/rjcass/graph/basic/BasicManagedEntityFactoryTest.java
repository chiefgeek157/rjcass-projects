package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
		List<ListenerEvent> events = new ArrayList<ListenerEvent>();
		events.add(ListenerEvent.MANAGED_ENTITY_FACTORY_GRAPH_CREATED);

		ManagedGraph graph = mFactory.createGraph();
		assertEquals(BasicGraph.class, graph.getClass());

		assertTrue(mListener.compareTo(events));
	}

	@Test
	public void testCreateNode()
	{
		List<ListenerEvent> events = new ArrayList<ListenerEvent>();
		events.add(ListenerEvent.MANAGED_ENTITY_FACTORY_NODE_CREATED);

		ManagedNode node = mFactory.createNode();
		assertEquals(BasicNode.class, node.getClass());

		assertTrue(mListener.compareTo(events));
	}

	@Test
	public void testCreateArc()
	{
		List<ListenerEvent> events = new ArrayList<ListenerEvent>();
		events.add(ListenerEvent.MANAGED_ENTITY_FACTORY_ARC_CREATED);

		ManagedArc arc = mFactory.createArc();
		assertEquals(BasicArc.class, arc.getClass());

		assertTrue(mListener.compareTo(events));
	}
}
