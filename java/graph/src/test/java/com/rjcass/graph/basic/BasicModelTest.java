package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
	private static ManagedModelFactory sModelFactory;

	private ManagedModel mModel;
	private EventTraceListener mListener;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		sModelFactory = new BasicModelFactory();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{}

	@Before
	public void setUp() throws Exception
	{
		mListener = new EventTraceListener();
		sModelFactory.addModelFactoryListener(mListener);
		sModelFactory.getEntityFactory().addListener(mListener);
		mModel = (ManagedModel)sModelFactory.createModel();
	}

	@After
	public void tearDown() throws Exception
	{
		sModelFactory.removeModelFactoryListener(mListener);
		sModelFactory.getEntityFactory().removeListener(mListener);
	}

	@Test
	public void testEmptyModel()
	{
		List<ListenerEvent> events = new ArrayList<ListenerEvent>();
		events.add(ListenerEvent.MODEL_FACTORY_MODEL_CREATED);

		Set<? extends Graph> graphs = mModel.getGraphs();
		assertEquals(0, graphs.size());

		Set<? extends Node> nodes = mModel.getNodes();
		assertEquals(0, nodes.size());

		Set<? extends Arc> arcs = mModel.getArcs();
		assertEquals(0, arcs.size());

		assertTrue(mListener.compareTo(events));
	}

	@Test
	public void testAddOneNode()
	{
		List<ListenerEvent> events = new ArrayList<ListenerEvent>();
		events.add(ListenerEvent.MODEL_FACTORY_MODEL_CREATED);
		events.add(ListenerEvent.MANAGED_ENTITY_FACTORY_GRAPH_CREATED);
		events.add(ListenerEvent.MODEL_GRAPH_ADDED);
		events.add(ListenerEvent.GRAPH_MODEL_SET);
		events.add(ListenerEvent.MANAGED_ENTITY_FACTORY_NODE_CREATED);
		events.add(ListenerEvent.GRAPH_NODE_ADDED);
		events.add(ListenerEvent.NODE_GRAPH_SET);

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

		assertTrue(mListener.compareTo(events));
	}

	@Test
	public void testAddTwoNodes()
	{
		List<ListenerEvent> events = new ArrayList<ListenerEvent>();
		events.add(ListenerEvent.MODEL_FACTORY_MODEL_CREATED);
		events.add(ListenerEvent.MANAGED_ENTITY_FACTORY_GRAPH_CREATED);
		events.add(ListenerEvent.MODEL_GRAPH_ADDED);
		events.add(ListenerEvent.GRAPH_MODEL_SET);
		events.add(ListenerEvent.MANAGED_ENTITY_FACTORY_NODE_CREATED);
		events.add(ListenerEvent.GRAPH_NODE_ADDED);
		events.add(ListenerEvent.NODE_GRAPH_SET);
		events.add(ListenerEvent.MANAGED_ENTITY_FACTORY_GRAPH_CREATED);
		events.add(ListenerEvent.MODEL_GRAPH_ADDED);
		events.add(ListenerEvent.GRAPH_MODEL_SET);
		events.add(ListenerEvent.MANAGED_ENTITY_FACTORY_NODE_CREATED);
		events.add(ListenerEvent.GRAPH_NODE_ADDED);
		events.add(ListenerEvent.NODE_GRAPH_SET);

		Node node1 = mModel.addNode();
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

		assertTrue(mListener.compareTo(events));
	}

	@Test
	public void testGetGraphsGraphFilter()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetNodesNodeFilter()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetArcsArcFilter()
	{
		fail("Not yet implemented");
	}
}
