package com.rjcass.graph;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.rjcass.graph.basic.BasicGraph;
import com.rjcass.graph.basic.BasicModel;
import com.rjcass.graph.basic.Node;

public class ModelTest
{
	@Test
	public void testIsValid()
	{
		BasicModel basicModel = new BasicModel();
		assertTrue(basicModel.isValid());
		basicModel.addNode();
		assertTrue(basicModel.isValid());
	}

	@Test
	public void testGetGraphCount()
	{
		BasicModel basicModel = new BasicModel();
		basicModel.addNode();
		basicModel.addNode();
		basicModel.addNode();
		basicModel.addNode();
		basicModel.addNode();
		assertTrue(basicModel.getGraphCount() == 5);
	}

	@Test
	public void testGetGraphs()
	{
		BasicModel basicModel = new BasicModel();
		basicModel.addNode();
		Set<BasicGraph> graphs = basicModel.getGraphs();
		assertTrue(graphs.size() == 1);
		Node node = basicModel.addNode();
		graphs = basicModel.getGraphs();
		assertTrue(graphs.contains(node.getGraph()));
	}

	@Test
	public void testAddNode()
	{
		BasicModel basicModel = new BasicModel();
		Node node = basicModel.addNode();
		assertTrue(node.getGraph().getModel() == basicModel);
		node = basicModel.addNode();
		assertTrue(node.getGraph().getModel() == basicModel);
		node = basicModel.addNode();
		assertTrue(node.getGraph().getModel() == basicModel);
	}
}
