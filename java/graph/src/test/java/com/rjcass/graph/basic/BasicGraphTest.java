package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.rjcass.graph.Graph;
import com.rjcass.graph.Node;
import com.rjcass.graph.listener.EventTraceListener;
import com.rjcass.graph.listener.ListenerEvent;
import com.rjcass.graph.managed.ManagedModel;
import com.rjcass.graph.managed.ManagedModelFactory;

public class BasicGraphTest
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
	public void testRemoveWithOneNode()
	{
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_REMOVED);
		events.addEvent(ListenerEvent.NODE_REMOVED);
		events.addEvent(ListenerEvent.GRAPH_MODEL_SET);
		events.addEvent(ListenerEvent.MODEL_GRAPH_REMOVED);
		events.addEvent(ListenerEvent.GRAPH_REMOVED);

		Node node1 = mModel.addNode();
		Graph node1Graph = node1.getGraph();

		mListener.resume();
		node1Graph.remove();
		assertFalse(node1Graph.isValid());

		Set<? extends Graph> modelGraphs = mModel.getGraphs();
		assertEquals(0, modelGraphs.size());

		Set<? extends Node> modelNodes = mModel.getNodes();
		assertEquals(0, modelNodes.size());

		assertEquals(events, mListener);
	}

	@Test
	public void testRemoveWithTwoNodes()
	{
		EventTraceListener events = new EventTraceListener();
		events.addEvent(ListenerEvent.NODE_GRAPH_SET);
		events.addEvent(ListenerEvent.GRAPH_NODE_REMOVED);
		events.addEvent(ListenerEvent.NODE_REMOVED);
		events.addEvent(ListenerEvent.GRAPH_MODEL_SET);
		events.addEvent(ListenerEvent.MODEL_GRAPH_REMOVED);
		events.addEvent(ListenerEvent.GRAPH_REMOVED);

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

		assertEquals(events, mListener);
	}

	@Ignore("Not yet implemented")
	@Test
	public void testGetGraphsGraphFilter()
	{
		fail("Not yet implemented");
	}

	@Ignore("Not yet implemented")
	@Test
	public void testGetNodesNodeFilter()
	{
		fail("Not yet implemented");
	}

	@Ignore("Not yet implemented")
	@Test
	public void testGetArcsArcFilter()
	{
		fail("Not yet implemented");
	}
}
