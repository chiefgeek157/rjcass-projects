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

import com.rjcass.graph.GArc;
import com.rjcass.graph.GArcFilter;
import com.rjcass.graph.GGraph;
import com.rjcass.graph.GNode;
import com.rjcass.graph.GNodeFilter;
import com.rjcass.graph.listener.EventTraceListener;
import com.rjcass.graph.listener.ListenerEventType;
import com.rjcass.graph.managed.ManagedGModel;
import com.rjcass.graph.managed.ManagedGModelFactory;
import com.rjcass.graph.managed.basic.BasicManagedGModelFactory;

public class BasicGGraphTest extends BasicTestBase
{
	private static final Logger sLog = Logger.getLogger(BasicGGraphTest.class);

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

		ManagedGModelFactory modelFactory = new BasicManagedGModelFactory();
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

		GNode node1 = mModel.addNode();
		GGraph node1Graph = node1.getGraph();

		mListener.resume();
		node1Graph.remove();
		assertFalse(node1Graph.isValid());

		Set<? extends GGraph> modelGraphs = mModel.getGraphs();
		assertEquals(0, modelGraphs.size());

		Set<? extends GNode> modelNodes = mModel.getNodes();
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

		GNode node1 = mModel.addNode();
		GNode node2 = mModel.addNode();

		GGraph node1Graph = node1.getGraph();

		mListener.resume();
		node1Graph.remove();
		assertFalse(node1Graph.isValid());

		Set<? extends GGraph> modelGraphs = mModel.getGraphs();
		assertEquals(1, modelGraphs.size());

		Set<? extends GNode> modelNodes = mModel.getNodes();
		assertEquals(1, modelNodes.size());
		assertEquals(node2, modelNodes.iterator().next());

		GGraph node2Graph = node2.getGraph();
		assertTrue(node2Graph.isValid());
		assertEquals(mModel, node2Graph.getModel());
		assertEquals(node2Graph, modelGraphs.iterator().next());

		Set<? extends GNode> node2GraphNodes = node2Graph.getNodes();
		assertEquals(1, node2GraphNodes.size());
		assertEquals(node2, node2GraphNodes.iterator().next());

		dump("testRemoveWithTwoNodes", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testGetNodesNodeFilter()
	{
		GNode node1 = mModel.addNode();
		GNode node2 = mModel.addNode();
		node1.joinTo(node2);

		Set<? extends GNode> allNodes = node1.getGraph().getNodes(new GNodeFilter()
		{
			public boolean passes(GNode node)
			{
				return true;
			}
		});
		assertTrue(allNodes.contains(node1));
		assertTrue(allNodes.contains(node2));

		Set<? extends GNode> noNodes = node1.getGraph().getNodes(new GNodeFilter()
		{
			public boolean passes(GNode node)
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
		GNode node1 = mModel.addNode();
		GNode node2 = mModel.addNode();
		GNode node3 = mModel.addNode();
		GArc arc1 = node1.joinTo(node2);
		GArc arc2 = node2.joinTo(node3);

		Set<? extends GArc> allArcs = node1.getGraph().getArcs(new GArcFilter()
		{
			public boolean passes(GArc arc)
			{
				return true;
			}
		});
		assertTrue(allArcs.contains(arc1));
		assertTrue(allArcs.contains(arc2));

		Set<? extends GArc> noArcs = node2.getGraph().getArcs(new GArcFilter()
		{
			public boolean passes(GArc arc)
			{
				return false;
			}
		});
		assertFalse(noArcs.contains(arc1));
		assertFalse(noArcs.contains(arc2));
	}
}
