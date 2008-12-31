package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	private ManagedModelFactory mFactory;
	private ManagedModel mModel;
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

		mFactory = new BasicModelFactory();
		mFactory.addModelFactoryListener(mListener);
		mFactory.getEntityFactory().addListener(mListener);
		mModel = mFactory.createManagedModel();
		mModel.addListener(mListener);
	}

	@After
	public void tearDown() throws Exception
	{}

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

		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		assertEquals(2, mModel.getGraphs().size());

		Graph graph1 = node1.getGraph();
		assertTrue(graph1.isValid());
		assertEquals(1, graph1.getNodes().size());

		Graph graph2 = node2.getGraph();
		assertTrue(graph2.isValid());
		assertEquals(1, graph2.getNodes().size());

		mListener.resume();
		node1.joinTo(node2);

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

		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		Node node3 = mModel.addNode();
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

		node1.joinTo(node2);

		assertEquals(2, mModel.getGraphs().size());

		assertFalse(graph1.isValid());

		assertTrue(graph2.isValid());
		assertEquals(2, graph2.getNodes().size());

		assertTrue(graph3.isValid());
		assertEquals(1, graph3.getNodes().size());

		mListener.resume();
		node2.joinTo(node3);

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

		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		Node node3 = mModel.addNode();
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

		node1.joinTo(node2);

		assertEquals(2, mModel.getGraphs().size());

		assertFalse(graph1.isValid());

		assertTrue(graph2.isValid());
		assertEquals(2, graph2.getNodes().size());

		assertTrue(graph3.isValid());
		assertEquals(1, graph3.getNodes().size());

		node2.joinTo(node3);

		assertEquals(1, mModel.getGraphs().size());

		assertTrue(graph2.isValid());
		assertEquals(3, graph2.getNodes().size());

		assertFalse(graph3.isValid());

		mListener.resume();
		node3.joinTo(node1);

		assertEquals(1, mModel.getGraphs().size());

		assertTrue(graph2.isValid());
		assertEquals(3, graph2.getNodes().size());

		assertEquals(events, mListener);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJoinToFailToSelf()
	{
		Node node1 = mModel.addNode();
		node1.joinTo(node1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJoinToFailToSameNode()
	{
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		node1.joinTo(node2);
		node1.joinTo(node2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJoinToFailInvalidNode()
	{
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		node2.remove();
		node1.joinTo(node2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJoinToFailWrongModel()
	{
		Node node1 = mModel.addNode();
		ManagedModel model2 = mFactory.createManagedModel();
		Node node2 = model2.addNode();
		node1.joinTo(node2);
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

		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		node1.joinTo(node2);

		mListener.resume();
		node1.disconnectFrom(node2);
		assertEquals(0, node1.getArcs().size());
		assertEquals(0, node2.getArcs().size());

		assertEquals(events, mListener);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDisconnectFromFailNotConnected()
	{
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
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

		Node node1 = mModel.addNode();

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

		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		node1.joinTo(node2);

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
		//         +-------+-----------+
		// 1---2   3   4---5---6   7   8---9
		//     +-----------+-------+
		//
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		Node node3 = mModel.addNode();
		Node node4 = mModel.addNode();
		Node node5 = mModel.addNode();
		Node node6 = mModel.addNode();
		Node node7 = mModel.addNode();
		Node node8 = mModel.addNode();
		Node node9 = mModel.addNode();
		node2.joinTo(node1);
		node5.joinTo(node2);
		node5.joinTo(node3);
		node5.joinTo(node4);
		node5.joinTo(node6);
		node5.joinTo(node7);
		node5.joinTo(node8);
		node8.joinTo(node9);

		assertEquals(1, mModel.getGraphs().size());
		
		mListener.resume();
		node5.remove();
		assertEquals(6, mModel.getGraphs().size());

		//assertEquals(events, mListener);
	}

	// @Test
	// public void testGetAdjacentNodes()
	// {
	// ManagedEntityFactory factory = new ManagedEntityFactory();
	// ManagedModel basicModel = factory.createModel();
	// Node node1 = basicModel.addNode();
	// Node node2 = basicModel.addNode();
	// Node node3 = basicModel.addNode();
	// Node node4 = basicModel.addNode();
	// Node node5 = basicModel.addNode();
	// Node node6 = basicModel.addNode();
	// node1.joinTo(node2);
	// node1.joinTo(node3);
	// node1.joinTo(node4);
	// node1.joinTo(node5);
	// node1.joinTo(node6);
	// Set<Node> nodes = node1.getAdjacentNodes();
	// assertTrue(nodes.size() == 5);
	// node2.remove();
	// nodes = node1.getAdjacentNodes();
	// assertTrue(nodes.size() == 4);
	// }
	//
	// @Test
	// public void testIsAdjacentTo()
	// {
	// ManagedEntityFactory factory = new ManagedEntityFactory();
	// ManagedModel basicModel = factory.createModel();
	// Node node1 = basicModel.addNode();
	// Node node2 = basicModel.addNode();
	// Node node3 = basicModel.addNode();
	// Node node4 = basicModel.addNode();
	// node1.joinTo(node2);
	// node2.joinTo(node3);
	// node3.joinTo(node4);
	// node4.joinTo(node1);
	// assertTrue(node1.isAdjacentTo(node2));
	// assertTrue(node1.isAdjacentTo(node4));
	// assertFalse(node1.isAdjacentTo(node3));
	// }
	//
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
