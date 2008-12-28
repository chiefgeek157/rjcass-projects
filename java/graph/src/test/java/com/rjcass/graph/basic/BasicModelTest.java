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
import org.junit.Test;

import com.rjcass.graph.Arc;
import com.rjcass.graph.Graph;
import com.rjcass.graph.Model;
import com.rjcass.graph.ModelFactory;
import com.rjcass.graph.ModelListener;
import com.rjcass.graph.Node;
import com.rjcass.graph.managed.ManagedModel;

public class BasicModelTest implements ModelListener
{
	private static ModelFactory sModelFactory;

	private ManagedModel mModel;

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
		mModel = (ManagedModel)sModelFactory.createModel();
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
	}

	@Test
	public void testAddTwoNodes()
	{
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
	public void testGetArcs()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetArcsArcFilter()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testAddArc()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveGraph()
	{
		fail("Not yet implemented");
	}

	@Override
	public void graphAdded(Model model, Graph graph)
	{
	// TODO Auto-generated method stub

	}

	@Override
	public void graphRemoved(Model model, Graph graph)
	{
	// TODO Auto-generated method stub

	}

	@Override
	public void graphSplit(Model model, Graph source, Graph target)
	{
	// TODO Auto-generated method stub

	}

	@Override
	public void graphsMerged(Model model, Graph source, Graph target)
	{
	// TODO Auto-generated method stub

	}

}
