package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.graph.GArc;
import com.rjcass.graph.GNode;
import com.rjcass.graph.GArc.Direction;
import com.rjcass.graph.listener.EventTraceListener;
import com.rjcass.graph.listener.ListenerEventType;
import com.rjcass.graph.managed.ManagedGModel;
import com.rjcass.graph.managed.ManagedGModelFactory;
import com.rjcass.graph.managed.basic.BasicManagedGModelFactory;

public class BasicGArcTest extends BasicTestBase
{
	private static Logger sLog = Logger.getLogger(BasicGArcTest.class);

	private ManagedGModelFactory mFactory;
	private ManagedGModel mModel;
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
		mListener.pause();

		mFactory = new BasicManagedGModelFactory();
		mFactory.addModelFactoryListener(mListener);
		mFactory.getEntityFactory().addListener(mListener);
		mModel = mFactory.createManagedModel();
		mModel.addListener(mListener);
	}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public void testSetUndirected()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.ARC_DIRECTED_SET, "arc1", false);

		GNode node1 = mModel.addNode();
		GNode node2 = mModel.addNode();
		GArc arc1 = node1.joinTo(node2, true);
		assertTrue(arc1.isDirected());

		mListener.resume();
		arc1.setUndirected();
		assertFalse(arc1.isDirected());

		dump("testSetUndirected", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testSetDirection()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.ARC_DIRECTED_SET, "arc1", true);
		events.addEvent(ListenerEventType.ARC_REVERSED, "arc1");
		events.addEvent(ListenerEventType.ARC_REVERSED, "arc1");

		GNode node1 = mModel.addNode();
		GNode node2 = mModel.addNode();
		GArc arc1 = node1.joinTo(node2);
		assertFalse(arc1.isDirected());

		mListener.resume();
		arc1.setDirection(node1, GArc.Direction.OUTBOUND);
		assertTrue(arc1.isDirected());
		assertEquals(node1, arc1.getStartNode());
		assertEquals(node2, arc1.getEndNode());

		arc1.setDirection(node2, GArc.Direction.OUTBOUND);
		assertTrue(arc1.isDirected());
		assertEquals(node2, arc1.getStartNode());
		assertEquals(node1, arc1.getEndNode());

		arc1.setDirection(node2, GArc.Direction.INBOUND);
		assertTrue(arc1.isDirected());
		assertEquals(node1, arc1.getStartNode());
		assertEquals(node2, arc1.getEndNode());

		dump("testSetDirection", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetDirectionFailWrongNode()
	{
		GNode node1 = mModel.addNode();
		GNode node2 = mModel.addNode();
		GNode node3 = mModel.addNode();
		GArc arc1 = node1.joinTo(node2);

		arc1.setDirection(node3, GArc.Direction.OUTBOUND);
	}

	@Test
	public void testReverse()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.ARC_REVERSED, "arc1");

		GNode node1 = mModel.addNode();
		GNode node2 = mModel.addNode();
		GArc arc1 = node1.joinTo(node2, true);

		mListener.resume();
		arc1.reverse();
		assertTrue(arc1.isDirected());
		assertEquals(node2, arc1.getStartNode());
		assertEquals(node1, arc1.getEndNode());

		dump("testReverse", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test(expected = IllegalStateException.class)
	public void testReverseFailNotDirected()
	{
		GNode node1 = mModel.addNode();
		GNode node2 = mModel.addNode();
		GArc arc1 = node1.joinTo(node2);

		arc1.reverse();
	}

	@Test
	public void testRemove()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_GRAPH_CREATED, "graph3");
		events.addEvent(ListenerEventType.GRAPH_MODEL_SET, "graph3", null, "model1");
		events.addEvent(ListenerEventType.MODEL_GRAPH_ADDED, "model1", "graph3");
		events.addEvent(ListenerEventType.NODE_GRAPH_SET, "node2", "graph2", null);
		events.addEvent(ListenerEventType.GRAPH_NODE_REMOVED, "graph2", "node2");
		events.addEvent(ListenerEventType.NODE_GRAPH_SET, "node2", null, "graph3");
		events.addEvent(ListenerEventType.GRAPH_NODE_ADDED, "graph3", "node2");
		events.addEvent(ListenerEventType.ARC_GRAPH_SET, "arc1", "graph2", null);
		events.addEvent(ListenerEventType.GRAPH_ARC_REMOVED, "graph2", "arc1");
		events.addEvent(ListenerEventType.MODEL_GRAPH_SPLIT, "model1", "graph2", "graph3");
		events.addEvent(ListenerEventType.NODE_ARC_REMOVED, "node1", "arc1");
		events.addEvent(ListenerEventType.NODE_ARC_REMOVED, "node2", "arc1");
		events.addEvent(ListenerEventType.ARC_REMOVED, "arc1");

		GNode node1 = mModel.addNode();
		GNode node2 = mModel.addNode();
		GArc arc1 = node1.joinTo(node2, true);

		mListener.resume();
		arc1.remove();
		assertFalse(arc1.isValid());
		assertFalse(mModel.getArcs().contains(arc1));
		assertFalse(node1.getGraph().getArcs().contains(arc1));
		assertFalse(node2.getGraph().getArcs().contains(arc1));
		assertFalse(node1.getArcs().contains(arc1));
		assertFalse(node2.getArcs().contains(arc1));

		dump("testRemove", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetOtherNodeFailNotConnected()
	{
		GNode node1 = mModel.addNode();
		GNode node2 = mModel.addNode();
		GNode node3 = mModel.addNode();
		GArc arc1 = node1.joinTo(node2);

		arc1.getOtherNode(node3);
	}

	@Test
	public void testGetDirection()
	{
		GNode node1 = mModel.addNode();
		GNode node2 = mModel.addNode();
		GNode node3 = mModel.addNode();
		GArc arc1 = node1.joinTo(node2);
		GArc arc2 = node2.joinTo(node3, true);

		assertEquals(Direction.UNDIRECTED, arc1.getDirection(node2));
		assertEquals(Direction.UNDIRECTED, arc1.getDirection(node1));
		assertEquals(Direction.INBOUND, arc2.getDirection(node3));
		assertEquals(Direction.OUTBOUND, arc2.getDirection(node2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDirectionFailNotConnected()
	{
		GNode node1 = mModel.addNode();
		GNode node2 = mModel.addNode();
		GNode node3 = mModel.addNode();
		GArc arc1 = node1.joinTo(node2);

		arc1.getDirection(node3);
	}
}
