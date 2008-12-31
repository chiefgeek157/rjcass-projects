package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.rjcass.graph.Arc;
import com.rjcass.graph.Graph;
import com.rjcass.graph.Node;
import com.rjcass.graph.listener.EventTraceListener;
import com.rjcass.graph.listener.ListenerEvent;
import com.rjcass.graph.managed.ManagedModel;
import com.rjcass.graph.managed.ManagedModelFactory;

public class BasicModelTest
{
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

		ManagedModelFactory modelFactory = new BasicModelFactory();
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
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.MANAGED_ENTITY_FACTORY_GRAPH_CREATED);
		events.addEvent(ListenerEvent.GRAPH_MODEL_SET);
		events.addEvent(ListenerEvent.MODEL_GRAPH_ADDED);
		events.addEvent(ListenerEvent.MANAGED_ENTITY_FACTORY_NODE_CREATED);
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_ADDED);

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

		mListener.dump(System.out);
		assertEquals(events, mListener);
	}

	@Test
	public void testAddTwoNodes()
	{
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.MANAGED_ENTITY_FACTORY_GRAPH_CREATED);
		events.addEvent(ListenerEvent.GRAPH_MODEL_SET);
		events.addEvent(ListenerEvent.MODEL_GRAPH_ADDED);
		events.addEvent(ListenerEvent.MANAGED_ENTITY_FACTORY_NODE_CREATED);
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_ADDED);

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

		assertEquals(events, mListener);
	}

	@Ignore("Not yet implemented") @Test
	public void testGetGraphsGraphFilter()
	{
		fail("Not yet implemented");
	}

	@Ignore("Not yet implemented") @Test
	public void testGetNodesNodeFilter()
	{
		fail("Not yet implemented");
	}

	@Ignore("Not yet implemented") @Test
	public void testGetArcsArcFilter()
	{
		fail("Not yet implemented");
	}
}
