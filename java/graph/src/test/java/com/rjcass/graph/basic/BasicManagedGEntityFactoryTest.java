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
import com.rjcass.graph.managed.ManagedGArc;
import com.rjcass.graph.managed.ManagedGEntityFactory;
import com.rjcass.graph.managed.ManagedGGraph;
import com.rjcass.graph.managed.ManagedGNode;
import com.rjcass.graph.managed.basic.BasicGArc;
import com.rjcass.graph.managed.basic.BasicGGraph;
import com.rjcass.graph.managed.basic.BasicManagedGEntityFactory;
import com.rjcass.graph.managed.basic.BasicGNode;

public class BasicManagedGEntityFactoryTest extends BasicTestBase
{
	private static final Logger sLog = Logger.getLogger(BasicManagedGEntityFactoryTest.class);

	private ManagedGEntityFactory mFactory;
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
		mFactory = new BasicManagedGEntityFactory();
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

		ManagedGGraph graph = mFactory.createGraph();
		assertEquals(BasicGGraph.class, graph.getClass());

		dump("testCreateGraph", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testCreateNode()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_NODE_CREATED, "node1");

		ManagedGNode node = mFactory.createNode();
		assertEquals(BasicGNode.class, node.getClass());

		dump("testCreateNode", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testCreateArc()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_ARC_CREATED, "arc1");

		ManagedGArc arc = mFactory.createArc();
		assertEquals(BasicGArc.class, arc.getClass());

		dump("testCreateArc", mListener, sLog);
		assertEquals(events, mListener);
	}
}
