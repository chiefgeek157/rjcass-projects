package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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

public class BasicGraphTest extends BasicTestBase
{
	private static final Logger sLog = Logger.getLogger(BasicGraphTest.class);

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

		ManagedModelFactory modelFactory = new BasicManagedModelFactory();
		modelFactory.addModelFactoryListener(mListener);
		modelFactory.getEntityFactory().addListener(mListener);
		mModel = modelFactory.createManagedModel();
		mModel.addListener(mListener);
	}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public void testRemoveWithOneNode()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.NODE_GRAPH_SET, "node1", "graph1", null);
		events.addEvent(ListenerEventType.GRAPH_NODE_REMOVED, "graph1", "node1");
		events.addEvent(ListenerEventType.NODE_REMOVED, "node1");
		events.addEvent(ListenerEventType.GRAPH_MODEL_SET, "graph1", "model1", null);
		events.addEvent(ListenerEventType.MODEL_GRAPH_REMOVED, "model1", "graph1");
		events.addEvent(ListenerEventType.GRAPH_REMOVED, "graph1");

		Node node1 = mModel.addNode();
		Graph node1Graph = node1.getGraph();

		mListener.resume();
		node1Graph.remove();
		assertFalse(node1Graph.isValid());

		Set<? extends Graph> modelGraphs = mModel.getGraphs();
		assertEquals(0, modelGraphs.size());

		Set<? extends Node> modelNodes = mModel.getNodes();
		assertEquals(0, modelNodes.size());

		dump("testRemoveWithOneNode", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testRemoveWithTwoNodes()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.NODE_GRAPH_SET, "node1", "graph1", null);
		events.addEvent(ListenerEventType.GRAPH_NODE_REMOVED, "graph1", "node1");
		events.addEvent(ListenerEventType.NODE_REMOVED, "node1");
		events.addEvent(ListenerEventType.GRAPH_MODEL_SET, "graph1", "model1", null);
		events.addEvent(ListenerEventType.MODEL_GRAPH_REMOVED, "model1", "graph1");
		events.addEvent(ListenerEventType.GRAPH_REMOVED, "graph1");

		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();

		Graph node1Graph = node1.getGraph();

		mListener.resume();
		node1Graph.remove();
		assertFalse(node1Graph.isValid());

		Set<? extends Graph> modelGraphs = mModel.getGraphs();
		assertEquals(1, modelGraphs.size());

		Set<? extends Node> modelNodes = mModel.getNodes();
		assertEquals(1, modelNodes.size());
		assertEquals(node2, modelNodes.iterator().next());

		Graph node2Graph = node2.getGraph();
		assertTrue(node2Graph.isValid());
		assertEquals(mModel, node2Graph.getModel());
		assertEquals(node2Graph, modelGraphs.iterator().next());

		Set<? extends Node> node2GraphNodes = node2Graph.getNodes();
		assertEquals(1, node2GraphNodes.size());
		assertEquals(node2, node2GraphNodes.iterator().next());

		dump("testRemoveWithTwoNodes", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testGetNodesNodeFilter()
	{
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		node1.joinTo(node2);

		Set<? extends Node> allNodes = node1.getGraph().getNodes(new NodeFilter()
		{
			public boolean passes(Node node)
			{
				return true;
			}
		});
		assertTrue(allNodes.contains(node1));
		assertTrue(allNodes.contains(node2));

		Set<? extends Node> noNodes = node1.getGraph().getNodes(new NodeFilter()
		{
			public boolean passes(Node node)
			{
				return false;
			}
		});
		assertFalse(noNodes.contains(node1.getGraph()));
		assertFalse(noNodes.contains(node2.getGraph()));
	}

	@Test
	public void testGetArcsArcFilter()
	{
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();
		Node node3 = mModel.addNode();
		Arc arc1 = node1.joinTo(node2);
		Arc arc2 = node2.joinTo(node3);

		Set<? extends Arc> allArcs = node1.getGraph().getArcs(new ArcFilter()
		{
			public boolean passes(Arc arc)
			{
				return true;
			}
		});
		assertTrue(allArcs.contains(arc1));
		assertTrue(allArcs.contains(arc2));

		Set<? extends Arc> noArcs = node2.getGraph().getArcs(new ArcFilter()
		{
			public boolean passes(Arc arc)
			{
				return false;
			}
		});
		assertFalse(noArcs.contains(arc1));
		assertFalse(noArcs.contains(arc2));
	}
}
