package com.rjcass.graph;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.rjcass.graph.basic.BasicModel;
import com.rjcass.graph.managed.ManagedModel;

public class ModelTest
{
	@Test
	public void testIsValid()
	{
		ManagedModel model = new BasicModel();
		assertTrue(model.isValid());
		model.addNode();
		assertTrue(model.isValid());
	}

	@Test
	public void testGetGraphCount()
	{
		ManagedModel model = new BasicModel();
		model.addNode();
		model.addNode();
		model.addNode();
		model.addNode();
		model.addNode();
		assertTrue(model.getGraphs().size() == 5);
	}

	@Test
	public void testGetGraphs()
	{
		ManagedModel model = new BasicModel();
		model.addNode();
		Set<? extends Graph> graphs = model.getGraphs();
		assertTrue(graphs.size() == 1);
		Node node = model.addNode();
		graphs = model.getGraphs();
		assertTrue(graphs.contains(node.getGraph()));
	}

	@Test
	public void testAddNode()
	{
		ManagedModel model = new BasicModel();
		Node node = model.addNode();
		assertTrue(node.getGraph().getModel() == model);
		node = model.addNode();
		assertTrue(node.getGraph().getModel() == model);
		node = model.addNode();
		assertTrue(node.getGraph().getModel() == model);
	}
}
