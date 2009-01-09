package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.graph.AdjacencyFilter;
import com.rjcass.graph.Arc;
import com.rjcass.graph.ArcFilter;
import com.rjcass.graph.Graph;
import com.rjcass.graph.Node;
import com.rjcass.graph.NodeFilter;
import com.rjcass.graph.listener.EventTraceListener;
import com.rjcass.graph.listener.ListenerEventType;
import com.rjcass.graph.managed.ManagedModel;
import com.rjcass.graph.managed.ManagedModelFactory;
import com.rjcass.graph.managed.basic.BasicManagedModelFactory;

public class BasicNodeTest extends BasicTestBase
{
	private static Logger sLog = Logger.getLogger(BasicNodeTest.class);

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

		mFactory = new BasicManagedModelFactory();
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
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_ARC_CREATED, "arc1");
		events.addEvent(ListenerEventType.ARC_NODES_SET, "arc1", null, null, "node1", "node2");
		events.addEvent(ListenerEventType.NODE_ARC_ADDED, "node1", "arc1");
		events.addEvent(ListenerEventType.NODE_ARC_ADDED, "node2", "arc1");
		events.addEvent(ListenerEventType.NODE_GRAPH_SET, "node1", "graph1", null);
		events.addEvent(ListenerEventType.GRAPH_NODE_REMOVED, "graph1", "node1");
		events.addEvent(ListenerEventType.GRAPH_MODEL_SET, "graph1", "model1", null);
		events.addEvent(ListenerEventType.MODEL_GRAPH_REMOVED, "model1", "graph1");
		events.addEvent(ListenerEventType.GRAPH_REMOVED, "graph1");
		events.addEvent(ListenerEventType.NODE_GRAPH_SET, "node1", null, "graph2");
		events.addEvent(ListenerEventType.GRAPH_NODE_ADDED, "graph2", "node1");
		events.addEvent(ListenerEventType.MODEL_GRAPHS_MERGED, "model1", "graph1", "graph2");
		events.addEvent(ListenerEventType.ARC_GRAPH_SET, "arc1", null, "graph2");
		events.addEvent(ListenerEventType.GRAPH_ARC_ADDED, "graph2", "arc1");

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

		dump("testJoinTo", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testJoinToTwoWay()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_ARC_CREATED, "arc2");
		events.addEvent(ListenerEventType.ARC_NODES_SET, "arc2", null, null, "node2", "node3");
		events.addEvent(ListenerEventType.NODE_ARC_ADDED, "node2", "arc2");
		events.addEvent(ListenerEventType.NODE_ARC_ADDED, "node3", "arc2");
		events.addEvent(ListenerEventType.NODE_GRAPH_SET, "node3", "graph3", null);
		events.addEvent(ListenerEventType.GRAPH_NODE_REMOVED, "graph3", "node3");
		events.addEvent(ListenerEventType.GRAPH_MODEL_SET, "graph3", "model1", null);
		events.addEvent(ListenerEventType.MODEL_GRAPH_REMOVED, "model1", "graph3");
		events.addEvent(ListenerEventType.GRAPH_REMOVED, "graph3");
		events.addEvent(ListenerEventType.NODE_GRAPH_SET, "node3", null, "graph2");
		events.addEvent(ListenerEventType.GRAPH_NODE_ADDED, "graph2", "node3");
		events.addEvent(ListenerEventType.MODEL_GRAPHS_MERGED, "model1", "graph3", "graph2");
		events.addEvent(ListenerEventType.ARC_GRAPH_SET, "arc2", null, "graph2");
		events.addEvent(ListenerEventType.GRAPH_ARC_ADDED, "graph2", "arc2");

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

		dump("testJoinToTwoWay", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testJoinToThreeWay()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_ARC_CREATED, "arc3");
		events.addEvent(ListenerEventType.ARC_NODES_SET, "arc3", null, null, "node3", "node1");
		events.addEvent(ListenerEventType.NODE_ARC_ADDED, "node3", "arc3");
		events.addEvent(ListenerEventType.NODE_ARC_ADDED, "node1", "arc3");
		events.addEvent(ListenerEventType.ARC_GRAPH_SET, "arc3", null, "graph2");
		events.addEvent(ListenerEventType.GRAPH_ARC_ADDED, "graph2", "arc3");

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

		dump("testJoinToThreeWay", mListener, sLog);
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

		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		node1.joinTo(node2);

		mListener.resume();
		node1.disconnectFrom(node2);
		assertEquals(0, node1.getArcs().size());
		assertEquals(0, node2.getArcs().size());

		dump("testDisconnectFrom", mListener, sLog);
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
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.NODE_GRAPH_SET, "node1", "graph1", null);
		events.addEvent(ListenerEventType.GRAPH_NODE_REMOVED, "graph1", "node1");
		events.addEvent(ListenerEventType.GRAPH_MODEL_SET, "graph1", "model1", null);
		events.addEvent(ListenerEventType.MODEL_GRAPH_REMOVED, "model1", "graph1");
		events.addEvent(ListenerEventType.GRAPH_REMOVED, "graph1");
		events.addEvent(ListenerEventType.NODE_REMOVED, "node1");

		Node node1 = mModel.addNode();

		mListener.resume();
		node1.remove();
		assertFalse(node1.isValid());
		assertEquals(0, mModel.getGraphs().size());

		dump("testRemoveTheOnlyNode", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testRemoveNotTheOnlyNode()
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
		events.addEvent(ListenerEventType.NODE_GRAPH_SET, "node1", "graph2", null);
		events.addEvent(ListenerEventType.GRAPH_NODE_REMOVED, "graph2", "node1");
		events.addEvent(ListenerEventType.GRAPH_MODEL_SET, "graph2", "model1", null);
		events.addEvent(ListenerEventType.MODEL_GRAPH_REMOVED, "model1", "graph2");
		events.addEvent(ListenerEventType.GRAPH_REMOVED, "graph2");
		events.addEvent(ListenerEventType.NODE_REMOVED, "node1");

		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		node1.joinTo(node2);

		mListener.resume();
		node1.remove();
		assertEquals(1, mModel.getGraphs().size());

		dump("testRemoveNotTheOnlyNode", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testRemovePartOfANetwork()
	{
		sLog.setLevel(Level.OFF);

		// Network
		//
		// ....+===========+=======+
		// 1===2...3...4===5===6...7...8===9
		// ........+=======+===========+
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

		node5.remove();
		assertEquals(6, mModel.getGraphs().size());
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
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		Node node3 = mModel.addNode();
		Node node4 = mModel.addNode();
		node1.joinTo(node2);
		node2.joinTo(node3);
		node3.joinTo(node4);
		node4.joinTo(node1);

		assertTrue(node1.isAdjacentTo(node2));
		assertTrue(node1.isAdjacentTo(node4));
		assertFalse(node1.isAdjacentTo(node3));
	}

	@Test
	public void testGetConnectingArc()
	{
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		Node node3 = mModel.addNode();
		Arc arc1 = node1.joinTo(node2);

		assertEquals(arc1, node1.getConnectingArc(node2));
		assertNull(node1.getConnectingArc(node3));
	}

	@Test
	public void testGetArcsWithArcFilter()
	{
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		Node node3 = mModel.addNode();
		Arc arc1 = node1.joinTo(node2);
		Arc arc2 = node1.joinTo(node3);

		Set<? extends Arc> allArcs = node1.getArcs(new ArcFilter()
		{
			public boolean passes(Arc arc)
			{
				return true;
			}
		});
		assertTrue(allArcs.contains(arc1));
		assertTrue(allArcs.contains(arc2));

		Set<? extends Arc> noArcs = node1.getArcs(new ArcFilter()
		{
			public boolean passes(Arc arc)
			{
				return false;
			}
		});
		assertFalse(noArcs.contains(arc1));
		assertFalse(noArcs.contains(arc2));
	}

	@Test
	public void testGetArcsWithNodeFilter()
	{
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		Node node3 = mModel.addNode();
		Arc arc1 = node1.joinTo(node2);
		Arc arc2 = node1.joinTo(node3);

		Set<? extends Arc> allArcs = node1.getArcs(new NodeFilter()
		{
			public boolean passes(Node node)
			{
				return true;
			}
		});
		assertTrue(allArcs.contains(arc1));
		assertTrue(allArcs.contains(arc2));

		Set<? extends Arc> noArcs = node1.getArcs(new NodeFilter()
		{
			public boolean passes(Node node)
			{
				return false;
			}
		});
		assertFalse(noArcs.contains(arc1));
		assertFalse(noArcs.contains(arc2));
	}

	@Test
	public void testGetArcsWithAdjacencyFilter()
	{
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		Node node3 = mModel.addNode();
		Arc arc1 = node1.joinTo(node2);
		Arc arc2 = node1.joinTo(node3);

		Set<? extends Arc> allArcs = node1.getArcs(new AdjacencyFilter()
		{
			public boolean passes(Arc arc, Node node)
			{
				return true;
			}
		});
		assertTrue(allArcs.contains(arc1));
		assertTrue(allArcs.contains(arc2));

		Set<? extends Arc> noArcs = node1.getArcs(new AdjacencyFilter()
		{
			public boolean passes(Arc arc, Node node)
			{
				return false;
			}
		});
		assertFalse(noArcs.contains(arc1));
		assertFalse(noArcs.contains(arc2));
	}

	@Test
	public void testGetNodesWithArcFilter()
	{
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		Node node3 = mModel.addNode();
		node1.joinTo(node2);
		node1.joinTo(node3);

		Set<? extends Node> allNodes = node1.getAdjacentNodes(new ArcFilter()
		{
			public boolean passes(Arc arc)
			{
				return true;
			}
		});
		assertTrue(allNodes.contains(node2));
		assertTrue(allNodes.contains(node3));

		Set<? extends Node> noNodes = node1.getAdjacentNodes(new ArcFilter()
		{
			public boolean passes(Arc arc)
			{
				return false;
			}
		});
		assertFalse(noNodes.contains(node2));
		assertFalse(noNodes.contains(node3));
	}

	@Test
	public void testGetNodesWithNodeFilter()
	{
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		Node node3 = mModel.addNode();
		node1.joinTo(node2);
		node1.joinTo(node3);

		Set<? extends Node> allNodes = node1.getAdjacentNodes(new NodeFilter()
		{
			public boolean passes(Node node)
			{
				return true;
			}
		});
		assertTrue(allNodes.contains(node2));
		assertTrue(allNodes.contains(node3));

		Set<? extends Node> noNodes = node1.getAdjacentNodes(new NodeFilter()
		{
			public boolean passes(Node node)
			{
				return false;
			}
		});
		assertFalse(noNodes.contains(node2));
		assertFalse(noNodes.contains(node3));
	}

	@Test
	public void testGetNodesWithAdjacencyFilter()
	{
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		Node node3 = mModel.addNode();
		node1.joinTo(node2);
		node1.joinTo(node3);

		Set<? extends Node> allNodes = node1.getAdjacentNodes(new AdjacencyFilter()
		{
			public boolean passes(Arc arc, Node node)
			{
				return true;
			}
		});
		assertTrue(allNodes.contains(node2));
		assertTrue(allNodes.contains(node3));

		Set<? extends Node> noNodes = node1.getAdjacentNodes(new AdjacencyFilter()
		{
			public boolean passes(Arc arc, Node node)
			{
				return false;
			}
		});
		assertFalse(noNodes.contains(node2));
		assertFalse(noNodes.contains(node3));
	}
}
