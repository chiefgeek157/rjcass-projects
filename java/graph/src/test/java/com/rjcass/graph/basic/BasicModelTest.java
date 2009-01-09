package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
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
import com.rjcass.graph.GraphFilter;
import com.rjcass.graph.Node;
import com.rjcass.graph.NodeFilter;
import com.rjcass.graph.listener.EventTraceListener;
import com.rjcass.graph.listener.ListenerEventType;
import com.rjcass.graph.managed.ManagedModel;
import com.rjcass.graph.managed.ManagedModelFactory;
import com.rjcass.graph.managed.basic.BasicManagedModelFactory;

public class BasicModelTest extends BasicTestBase
{
	private static final Logger sLog = Logger.getLogger(BasicModelTest.class);

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
	public void testEmptyModel()
	{
		Set<? extends Graph> graphs = mModel.getGraphs();
		assertEquals(0, graphs.size());

		Set<? extends Node> nodes = mModel.getNodes();
		assertEquals(0, nodes.size());

		Set<? extends Arc> arcs = mModel.getArcs();
		assertEquals(0, arcs.size());
	}

	@Test
	public void testAddOneNode()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_GRAPH_CREATED, "graph1");
		events.addEvent(ListenerEventType.GRAPH_MODEL_SET, "graph1", null, "model1");
		events.addEvent(ListenerEventType.MODEL_GRAPH_ADDED, "model1", "graph1");
		events.addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_NODE_CREATED, "node1");
		events.addEvent(ListenerEventType.NODE_GRAPH_SET, "node1", null, "graph1");
		events.addEvent(ListenerEventType.GRAPH_NODE_ADDED, "graph1", "node1");

		mListener.resume();

		Node node1 = mModel.addNode();

		Graph node1Graph = node1.getGraph();
		assertEquals(mModel, node1Graph.getModel());

		Set<? extends Graph> modelGraphs = mModel.getGraphs();
		assertEquals(1, modelGraphs.size());
		assertEquals(node1Graph, modelGraphs.iterator().next());

		Set<? extends Node> modelNodes = mModel.getNodes();
		assertEquals(1, modelNodes.size());
		assertEquals(node1, modelNodes.iterator().next());

		Set<? extends Node> node1GraphNodes = node1Graph.getNodes();
		assertEquals(1, node1GraphNodes.size());
		assertEquals(node1, node1GraphNodes.iterator().next());

		dump("testAddOneNode", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testAddTwoNodes()
	{
		sLog.setLevel(Level.OFF);

		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_GRAPH_CREATED, "graph2");
		events.addEvent(ListenerEventType.GRAPH_MODEL_SET, "graph2", null, "model1");
		events.addEvent(ListenerEventType.MODEL_GRAPH_ADDED, "model1", "graph2");
		events.addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_NODE_CREATED, "node2");
		events.addEvent(ListenerEventType.NODE_GRAPH_SET, "node2", null, "graph2");
		events.addEvent(ListenerEventType.GRAPH_NODE_ADDED, "graph2", "node2");

		Node node1 = mModel.addNode();

		mListener.resume();
		Node node2 = mModel.addNode();

		Graph node1Graph = node1.getGraph();
		assertEquals(mModel, node1Graph.getModel());

		Graph node2Graph = node2.getGraph();
		assertEquals(mModel, node2Graph.getModel());

		Set<? extends Graph> modelGraphs = mModel.getGraphs();
		assertEquals(2, modelGraphs.size());
		Iterator<? extends Graph> modelGraphIterator = modelGraphs.iterator();
		Graph modelGraph1 = modelGraphIterator.next();
		Graph modelGraph2 = modelGraphIterator.next();
		assertTrue(modelGraph1 == node1Graph && modelGraph2 == node2Graph || modelGraph1 == node2Graph && modelGraph2 == node1Graph);

		Set<? extends Node> modelNodes = mModel.getNodes();
		assertEquals(2, modelNodes.size());
		Iterator<? extends Node> modelNodeIterator = modelNodes.iterator();
		Node modelNode1 = modelNodeIterator.next();
		Node modelNode2 = modelNodeIterator.next();
		assertTrue(modelNode1 == node1 && modelNode2 == node2 || modelNode1 == node2 && modelNode2 == node1);

		Set<? extends Node> node1GraphNodes = node1Graph.getNodes();
		assertEquals(1, node1GraphNodes.size());
		assertEquals(node1, node1GraphNodes.iterator().next());

		Set<? extends Node> node2GraphNodes = node2Graph.getNodes();
		assertEquals(1, node2GraphNodes.size());
		assertEquals(node2, node2GraphNodes.iterator().next());

		dump("testAddTwoNodes", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testGetGraphsGraphFilter()
	{
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();

		Set<? extends Graph> allGraphs = mModel.getGraphs(new GraphFilter()
		{
			public boolean passes(Graph graph)
			{
				return true;
			}
		});
		assertTrue(allGraphs.contains(node1.getGraph()));
		assertTrue(allGraphs.contains(node2.getGraph()));

		Set<? extends Graph> noGraphs = mModel.getGraphs(new GraphFilter()
		{
			public boolean passes(Graph graph)
			{
				return false;
			}
		});
		assertFalse(noGraphs.contains(node1.getGraph()));
		assertFalse(noGraphs.contains(node2.getGraph()));
	}

	@Test
	public void testGetNodesNodeFilter()
	{
		Node node1 = mModel.addNode();
		Node node2 = mModel.addNode();

		Set<? extends Node> allNodes = mModel.getNodes(new NodeFilter()
		{
			public boolean passes(Node node)
			{
				return true;
			}
		});
		assertTrue(allNodes.contains(node1));
		assertTrue(allNodes.contains(node2));

		Set<? extends Node> noNodes = mModel.getNodes(new NodeFilter()
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

		Set<? extends Arc> allArcs = mModel.getArcs(new ArcFilter()
		{
			public boolean passes(Arc arc)
			{
				return true;
			}
		});
		assertTrue(allArcs.contains(arc1));
		assertTrue(allArcs.contains(arc2));

		Set<? extends Arc> noArcs = mModel.getArcs(new ArcFilter()
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
