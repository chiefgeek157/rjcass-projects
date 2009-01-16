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

import com.rjcass.graph.GArc;
import com.rjcass.graph.GArcFilter;
import com.rjcass.graph.GGraph;
import com.rjcass.graph.GGraphFilter;
import com.rjcass.graph.GNode;
import com.rjcass.graph.GNodeFilter;
import com.rjcass.graph.listener.EventTraceListener;
import com.rjcass.graph.listener.ListenerEventType;
import com.rjcass.graph.managed.ManagedGModel;
import com.rjcass.graph.managed.ManagedGModelFactory;
import com.rjcass.graph.managed.basic.BasicManagedGModelFactory;

public class BasicGModelTest extends BasicTestBase
{
	private static final Logger sLog = Logger.getLogger(BasicGModelTest.class);

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
	public void testEmptyModel()
	{
		Set<? extends GGraph> graphs = mModel.getGraphs();
		assertEquals(0, graphs.size());

		Set<? extends GNode> nodes = mModel.getNodes();
		assertEquals(0, nodes.size());

		Set<? extends GArc> arcs = mModel.getArcs();
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

		GNode node1 = mModel.addNode();

		GGraph node1Graph = node1.getGraph();
		assertEquals(mModel, node1Graph.getModel());

		Set<? extends GGraph> modelGraphs = mModel.getGraphs();
		assertEquals(1, modelGraphs.size());
		assertEquals(node1Graph, modelGraphs.iterator().next());

		Set<? extends GNode> modelNodes = mModel.getNodes();
		assertEquals(1, modelNodes.size());
		assertEquals(node1, modelNodes.iterator().next());

		Set<? extends GNode> node1GraphNodes = node1Graph.getNodes();
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

		GNode node1 = mModel.addNode();

		mListener.resume();
		GNode node2 = mModel.addNode();

		GGraph node1Graph = node1.getGraph();
		assertEquals(mModel, node1Graph.getModel());

		GGraph node2Graph = node2.getGraph();
		assertEquals(mModel, node2Graph.getModel());

		Set<? extends GGraph> modelGraphs = mModel.getGraphs();
		assertEquals(2, modelGraphs.size());
		Iterator<? extends GGraph> modelGraphIterator = modelGraphs.iterator();
		GGraph modelGraph1 = modelGraphIterator.next();
		GGraph modelGraph2 = modelGraphIterator.next();
		assertTrue(modelGraph1 == node1Graph && modelGraph2 == node2Graph || modelGraph1 == node2Graph && modelGraph2 == node1Graph);

		Set<? extends GNode> modelNodes = mModel.getNodes();
		assertEquals(2, modelNodes.size());
		Iterator<? extends GNode> modelNodeIterator = modelNodes.iterator();
		GNode modelNode1 = modelNodeIterator.next();
		GNode modelNode2 = modelNodeIterator.next();
		assertTrue(modelNode1 == node1 && modelNode2 == node2 || modelNode1 == node2 && modelNode2 == node1);

		Set<? extends GNode> node1GraphNodes = node1Graph.getNodes();
		assertEquals(1, node1GraphNodes.size());
		assertEquals(node1, node1GraphNodes.iterator().next());

		Set<? extends GNode> node2GraphNodes = node2Graph.getNodes();
		assertEquals(1, node2GraphNodes.size());
		assertEquals(node2, node2GraphNodes.iterator().next());

		dump("testAddTwoNodes", mListener, sLog);
		assertEquals(events, mListener);
	}

	@Test
	public void testGetGraphsGraphFilter()
	{
		GNode node1 = mModel.addNode();
		GNode node2 = mModel.addNode();

		Set<? extends GGraph> allGraphs = mModel.getGraphs(new GGraphFilter()
		{
			public boolean passes(GGraph graph)
			{
				return true;
			}
		});
		assertTrue(allGraphs.contains(node1.getGraph()));
		assertTrue(allGraphs.contains(node2.getGraph()));

		Set<? extends GGraph> noGraphs = mModel.getGraphs(new GGraphFilter()
		{
			public boolean passes(GGraph graph)
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
		GNode node1 = mModel.addNode();
		GNode node2 = mModel.addNode();

		Set<? extends GNode> allNodes = mModel.getNodes(new GNodeFilter()
		{
			public boolean passes(GNode node)
			{
				return true;
			}
		});
		assertTrue(allNodes.contains(node1));
		assertTrue(allNodes.contains(node2));

		Set<? extends GNode> noNodes = mModel.getNodes(new GNodeFilter()
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

		Set<? extends GArc> allArcs = mModel.getArcs(new GArcFilter()
		{
			public boolean passes(GArc arc)
			{
				return true;
			}
		});
		assertTrue(allArcs.contains(arc1));
		assertTrue(allArcs.contains(arc2));

		Set<? extends GArc> noArcs = mModel.getArcs(new GArcFilter()
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
