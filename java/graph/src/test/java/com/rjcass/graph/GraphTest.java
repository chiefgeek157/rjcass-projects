package com.rjcass.graph;

import static org.junit.Assert.assertFalse;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.rjcass.graph.basic.BasicModel;
import com.rjcass.graph.managed.ManagedModel;

public class GraphTest
{
	private ManagedModel mBasicModel;
	private Node mNode;

	@Before
	public void setUp()
	{
		mBasicModel = new BasicModel();
		mNode = mBasicModel.addNode();
	}

	@Test
	public void testRemove()
	{
		Graph graph = mNode.getGraph();
		graph.remove();
		assertFalse(graph.isValid());
		Set<? extends Graph> graphs = mBasicModel.getGraphs();
		assertFalse(graphs.contains(graph));
	}
}
