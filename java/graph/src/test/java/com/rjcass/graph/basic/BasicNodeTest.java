package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.graph.Graph;
import com.rjcass.graph.Node;
import com.rjcass.graph.listener.EventTraceListener;
import com.rjcass.graph.listener.ListenerEvent;
import com.rjcass.graph.managed.ManagedModel;
import com.rjcass.graph.managed.ManagedModelFactory;

public class BasicNodeTest
{
	private static Log sLog = LogFactory.getLog(BasicNodeTest.class);

	private ManagedModelFactory mFactory;
	private ManagedModel mModel;
	private EventTraceListener mListener;
	private ConsoleAppender mAppender;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{}

	@Before
	public void setUp() throws Exception
	{
		PatternLayout layout = new PatternLayout("%r: %m\n");
		mAppender = new ConsoleAppender(layout, ConsoleAppender.SYSTEM_OUT);

		Logger.getRootLogger().addAppender(mAppender);

		mListener = new EventTraceListener();
		mListener.pause();

		mFactory = new BasicManagedModelFactory();
		mFactory.addModelFactoryListener(mListener);
		mFactory.getEntityFactory().addListener(mListener);
		mModel = mFactory.createManagedModel("test");
		mModel.addListener(mListener);
	}

	@After
	public void tearDown() throws Exception
	{
		Logger.getRootLogger().removeAppender(mAppender);
	}

	@Test
	public void testJoinTo()
	{
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.MANAGED_ENTITY_FACTORY_ARC_CREATED);
		events.addEvent(ListenerEvent.ARC_NODES_SET);
		events.addEvent(ListenerEvent.NODE_ARC_ADDED);
		events.addEvent(ListenerEvent.NODE_ARC_ADDED);
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_REMOVED);
		events.addEvent(ListenerEvent.GRAPH_MODEL_SET);
		events.addEvent(ListenerEvent.MODEL_GRAPH_REMOVED);
		events.addEvent(ListenerEvent.GRAPH_REMOVED);
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_ADDED);
		events.addEvent(ListenerEvent.MODEL_GRAPHS_MERGED);
		events.addEvent(ListenerEvent.ARC_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_ARC_ADDED);

		Node node1 = mModel.addNode("node1");
		Node node2 = mModel.addNode("node2");
		assertEquals(2, mModel.getGraphs().size());

		Graph graph1 = node1.getGraph();
		assertTrue(graph1.isValid());
		assertEquals(1, graph1.getNodes().size());

		Graph graph2 = node2.getGraph();
		assertTrue(graph2.isValid());
		assertEquals(1, graph2.getNodes().size());

		mListener.resume();
		node1.joinTo("arc1", node2);

		assertEquals(1, mModel.getGraphs().size());

		assertFalse(graph1.isValid());

		assertTrue(graph2.isValid());
		assertEquals(2, graph2.getNodes().size());

		assertEquals(events, mListener);
	}

	@Test
	public void testJoinToTwoWay()
	{
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.MANAGED_ENTITY_FACTORY_ARC_CREATED);
		events.addEvent(ListenerEvent.ARC_NODES_SET);
		events.addEvent(ListenerEvent.NODE_ARC_ADDED);
		events.addEvent(ListenerEvent.NODE_ARC_ADDED);
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_REMOVED);
		events.addEvent(ListenerEvent.GRAPH_MODEL_SET);
		events.addEvent(ListenerEvent.MODEL_GRAPH_REMOVED);
		events.addEvent(ListenerEvent.GRAPH_REMOVED);
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_ADDED);
		events.addEvent(ListenerEvent.MODEL_GRAPHS_MERGED);
		events.addEvent(ListenerEvent.ARC_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_ARC_ADDED);

		Node node1 = mModel.addNode("node1");
		Node node2 = mModel.addNode("node2");
		Node node3 = mModel.addNode("node3");
		assertEquals(3, mModel.getGraphs().size());

		Graph graph1 = node1.getGraph();
		assertTrue(graph1.isValid());
		assertEquals(1, graph1.getNodes().size());

		Graph graph2 = node2.getGraph();
		assertTrue(graph2.isValid());
		assertEquals(1, graph2.getNodes().size());

		Graph graph3 = node3.getGraph();
		assertTrue(graph3.isValid());
		assertEquals(1, graph3.getNodes().size());

		node1.joinTo("arc1", node2);

		assertEquals(2, mModel.getGraphs().size());

		assertFalse(graph1.isValid());

		assertTrue(graph2.isValid());
		assertEquals(2, graph2.getNodes().size());

		assertTrue(graph3.isValid());
		assertEquals(1, graph3.getNodes().size());

		mListener.resume();
		node2.joinTo("arc2", node3);

		assertEquals(1, mModel.getGraphs().size());

		assertTrue(graph2.isValid());
		assertEquals(3, graph2.getNodes().size());

		assertFalse(graph3.isValid());

		assertEquals(events, mListener);
	}

	@Test
	public void testJoinToThreeWay()
	{
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.MANAGED_ENTITY_FACTORY_ARC_CREATED);
		events.addEvent(ListenerEvent.ARC_NODES_SET);
		events.addEvent(ListenerEvent.NODE_ARC_ADDED);
		events.addEvent(ListenerEvent.NODE_ARC_ADDED);
		events.addEvent(ListenerEvent.ARC_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_ARC_ADDED);

		Node node1 = mModel.addNode("node1");
		Node node2 = mModel.addNode("node2");
		Node node3 = mModel.addNode("node3");
		assertEquals(3, mModel.getGraphs().size());

		Graph graph1 = node1.getGraph();
		assertTrue(graph1.isValid());
		assertEquals(1, graph1.getNodes().size());

		Graph graph2 = node2.getGraph();
		assertTrue(graph2.isValid());
		assertEquals(1, graph2.getNodes().size());

		Graph graph3 = node3.getGraph();
		assertTrue(graph3.isValid());
		assertEquals(1, graph3.getNodes().size());

		node1.joinTo("arc1", node2);

		assertEquals(2, mModel.getGraphs().size());

		assertFalse(graph1.isValid());

		assertTrue(graph2.isValid());
		assertEquals(2, graph2.getNodes().size());

		assertTrue(graph3.isValid());
		assertEquals(1, graph3.getNodes().size());

		node2.joinTo("arc2", node3);

		assertEquals(1, mModel.getGraphs().size());

		assertTrue(graph2.isValid());
		assertEquals(3, graph2.getNodes().size());

		assertFalse(graph3.isValid());

		mListener.resume();
		node3.joinTo("arc3", node1);

		assertEquals(1, mModel.getGraphs().size());

		assertTrue(graph2.isValid());
		assertEquals(3, graph2.getNodes().size());

		assertEquals(events, mListener);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJoinToFailToSelf()
	{
		Node node1 = mModel.addNode("node1");
		node1.joinTo("arc1", node1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJoinToFailToSameNode()
	{
		Node node1 = mModel.addNode("node1");
		Node node2 = mModel.addNode("node2");
		node1.joinTo("arc1", node2);
		node1.joinTo("arc2", node2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJoinToFailInvalidNode()
	{
		Node node1 = mModel.addNode("node1");
		Node node2 = mModel.addNode("node2");
		node2.remove();
		node1.joinTo("arc1", node2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJoinToFailWrongModel()
	{
		Node node1 = mModel.addNode("node1");
		ManagedModel model2 = mFactory.createManagedModel("test2");
		Node node2 = model2.addNode("node2");
		node1.joinTo("arc1", node2);
	}

	@Test
	public void testDisconnectFrom()
	{
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.MANAGED_ENTITY_FACTORY_GRAPH_CREATED);
		events.addEvent(ListenerEvent.GRAPH_MODEL_SET);
		events.addEvent(ListenerEvent.MODEL_GRAPH_ADDED);
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_REMOVED);
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_ADDED);
		events.addEvent(ListenerEvent.ARC_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_ARC_REMOVED);
		events.addEvent(ListenerEvent.MODEL_GRAPH_SPLIT);
		events.addEvent(ListenerEvent.NODE_ARC_REMOVED);
		events.addEvent(ListenerEvent.NODE_ARC_REMOVED);
		events.addEvent(ListenerEvent.ARC_REMOVED);

		Node node1 = mModel.addNode("node1");
		Node node2 = mModel.addNode("node2");
		node1.joinTo("arc1", node2);

		mListener.resume();
		node1.disconnectFrom(node2);
		assertEquals(0, node1.getArcs().size());
		assertEquals(0, node2.getArcs().size());

		assertEquals(events, mListener);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDisconnectFromFailNotConnected()
	{
		Node node1 = mModel.addNode("node1");
		Node node2 = mModel.addNode("node2");
		node1.disconnectFrom(node2);
	}

	@Test
	public void testRemoveTheOnlyNode()
	{
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_REMOVED);
		events.addEvent(ListenerEvent.GRAPH_MODEL_SET);
		events.addEvent(ListenerEvent.MODEL_GRAPH_REMOVED);
		events.addEvent(ListenerEvent.GRAPH_REMOVED);
		events.addEvent(ListenerEvent.NODE_REMOVED);

		Node node1 = mModel.addNode("node1");

		mListener.resume();
		node1.remove();
		assertFalse(node1.isValid());
		assertEquals(0, mModel.getGraphs().size());

		assertEquals(events, mListener);
	}

	@Test
	public void testRemoveNotTheOnlyNode()
	{
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.MANAGED_ENTITY_FACTORY_GRAPH_CREATED);
		events.addEvent(ListenerEvent.GRAPH_MODEL_SET);
		events.addEvent(ListenerEvent.MODEL_GRAPH_ADDED);
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_REMOVED);
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_ADDED);
		events.addEvent(ListenerEvent.ARC_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_ARC_REMOVED);
		events.addEvent(ListenerEvent.MODEL_GRAPH_SPLIT);
		events.addEvent(ListenerEvent.NODE_ARC_REMOVED);
		events.addEvent(ListenerEvent.NODE_ARC_REMOVED);
		events.addEvent(ListenerEvent.ARC_REMOVED);
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_REMOVED);
		events.addEvent(ListenerEvent.GRAPH_MODEL_SET);
		events.addEvent(ListenerEvent.MODEL_GRAPH_REMOVED);
		events.addEvent(ListenerEvent.GRAPH_REMOVED);
		events.addEvent(ListenerEvent.NODE_REMOVED);

		Node node1 = mModel.addNode("node1");
		Node node2 = mModel.addNode("node2");
		node1.joinTo("arc1", node2);

		mListener.resume();
		node1.remove();
		assertEquals(1, mModel.getGraphs().size());

		assertEquals(events, mListener);
	}

	@Test
	public void testRemovePartOfANetwork()
	{
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.MANAGED_ENTITY_FACTORY_GRAPH_CREATED);
		events.addEvent(ListenerEvent.GRAPH_MODEL_SET);
		events.addEvent(ListenerEvent.MODEL_GRAPH_ADDED);
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_REMOVED);
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_ADDED);
		events.addEvent(ListenerEvent.ARC_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_ARC_REMOVED);
		events.addEvent(ListenerEvent.NODE_ARC_REMOVED);
		events.addEvent(ListenerEvent.NODE_ARC_REMOVED);
		events.addEvent(ListenerEvent.ARC_REMOVED);
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_REMOVED);
		events.addEvent(ListenerEvent.GRAPH_MODEL_SET);
		events.addEvent(ListenerEvent.MODEL_GRAPH_REMOVED);
		events.addEvent(ListenerEvent.GRAPH_REMOVED);
		events.addEvent(ListenerEvent.NODE_REMOVED);

		// Network
		//
		// ....+===========+=======+
		// 1===2...3...4===5===6...7...8===9
		// ........+=======+===========+
		//
		Node node1 = mModel.addNode("node1");
		Node node2 = mModel.addNode("node2");
		Node node3 = mModel.addNode("node3");
		Node node4 = mModel.addNode("node4");
		Node node5 = mModel.addNode("node5");
		Node node6 = mModel.addNode("node6");
		Node node7 = mModel.addNode("node7");
		Node node8 = mModel.addNode("node8");
		Node node9 = mModel.addNode("node9");
		node2.joinTo("arc1", node1);
		node5.joinTo("arc2", node2);
		node5.joinTo("arc3", node3);
		node5.joinTo("arc4", node4);
		node5.joinTo("arc5", node6);
		node5.joinTo("arc6", node7);
		node5.joinTo("arc7", node8);
		node8.joinTo("arc8", node9);

		assertEquals(1, mModel.getGraphs().size());

		mListener.resume();
		node5.remove();
		assertEquals(6, mModel.getGraphs().size());

		mListener.dump(sLog);
		// assertEquals(events, mListener);
	}

	@Test
	public void testGetAdjacentNodes()
	{
		// Network
		//
		// ....+===========+=======+
		// 1===2...3...4===5===6...7...8===9
		// ........+=======+===========+
		//
		Node node1 = mModel.addNode("node1");
		Node node2 = mModel.addNode("node2");
		Node node3 = mModel.addNode("node3");
		Node node4 = mModel.addNode("node4");
		Node node5 = mModel.addNode("node5");
		Node node6 = mModel.addNode("node6");
		Node node7 = mModel.addNode("node7");
		Node node8 = mModel.addNode("node8");
		Node node9 = mModel.addNode("node9");
		node2.joinTo("arc1", node1);
		node5.joinTo("arc2", node2);
		node5.joinTo("arc3", node3);
		node5.joinTo("arc4", node4);
		node5.joinTo("arc5", node6);
		node5.joinTo("arc6", node7);
		node5.joinTo("arc7", node8);
		node8.joinTo("arc8", node9);

		Set<? extends Node> nodes = node5.getAdjacentNodes();
		assertTrue(nodes.size() == 6);
		assertTrue(nodes.contains(node2));
		assertTrue(nodes.contains(node3));
		assertTrue(nodes.contains(node4));
		assertTrue(nodes.contains(node6));
		assertTrue(nodes.contains(node7));
		assertTrue(nodes.contains(node8));
	}

	@Test
	public void testIsAdjacentTo()
	{
		Node node1 = mModel.addNode("node1");
		Node node2 = mModel.addNode("node2");
		Node node3 = mModel.addNode("node3");
		Node node4 = mModel.addNode("node4");
		node1.joinTo("arc1", node2);
		node2.joinTo("arc2", node3);
		node3.joinTo("arc3", node4);
		node4.joinTo("arc4", node1);

		assertTrue(node1.isAdjacentTo(node2));
		assertTrue(node1.isAdjacentTo(node4));
		assertFalse(node1.isAdjacentTo(node3));
	}

	// @Test
	// public void testGetAdjacentArc()
	// {
	// ManagedEntityFactory factory = new ManagedEntityFactory();
	// ManagedModel basicModel = factory.createModel();
	// Node node1 = basicModel.addNode();
	// Node node2 = basicModel.addNode();
	// Node node3 = basicModel.addNode();
	// Node node4 = basicModel.addNode();
	// Arc arc1 = node1.joinTo(node2);
	// node2.joinTo(node3);
	// node3.joinTo(node4);
	// Arc arc4 = node4.joinTo(node1);
	// assertTrue(node1.getConnectingArc(node2) == arc1);
	// assertTrue(node1.getConnectingArc(node4) == arc4);
	// }
	//
	// @Test
	// public void testGetAdjacentArcWithFilter()
	// {
	// ManagedEntityFactory factory = new ManagedEntityFactory();
	// ManagedModel basicModel = factory.createModel();
	// Node node1 = basicModel.addNode();
	// Node node2 = basicModel.addNode();
	// Node node3 = basicModel.addNode();
	// Node node4 = basicModel.addNode();
	// Arc arc1 = node1.joinTo(node2);
	// node2.joinTo(node3);
	// node3.joinTo(node4);
	// Arc arc4 = node4.joinTo(node1);
	// assertTrue(node1.getConnectingArc(node2) == arc1);
	// assertTrue(node1.getConnectingArc(node4) == arc4);
	// }
}
