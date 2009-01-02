package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.graph.listener.EventTraceListener;
import com.rjcass.graph.listener.ListenerEventType;
import com.rjcass.graph.managed.ManagedArc;
import com.rjcass.graph.managed.ManagedEntityFactory;
import com.rjcass.graph.managed.ManagedGraph;
import com.rjcass.graph.managed.ManagedNode;

public class BasicManagedEntityFactoryTest extends BasicTestBase
{
	private static final Logger sLog = Logger.getLogger(BasicManagedEntityFactoryTest.class);

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
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_GRAPH_CREATED, "graph1");

		ManagedGraph graph = mFactory.createGraph();
		assertEquals(BasicGraph.class, graph.getClass());

		dump("testCreateGraph", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testCreateNode()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_NODE_CREATED, "node1");

		ManagedNode node = mFactory.createNode();
		assertEquals(BasicNode.class, node.getClass());

		dump("testCreateNode", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testCreateArc()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_ARC_CREATED, "arc1");

		ManagedArc arc = mFactory.createArc();
		assertEquals(BasicArc.class, arc.getClass());

		dump("testCreateArc", mListener, sLog);
		assertEquals(events, mListener);
	}
}
