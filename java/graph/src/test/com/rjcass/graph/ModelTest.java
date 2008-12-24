package com.rjcass.graph;


import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;


public class ModelTest
{
    @Test public void testIsValid()
    {
        Model model = new Model();
        assertTrue(model.isValid());
        model.addNode();
        assertTrue(model.isValid());
    }

    @Test public void testGetGraphCount()
    {
        Model model = new Model();
        model.addNode();
        model.addNode();
        model.addNode();
        model.addNode();
        model.addNode();
        assertTrue(model.getGraphCount() == 5);
    }

    @Test public void testGetGraphs()
    {
        Model model = new Model();
        model.addNode();
        Set<Graph> graphs = model.getGraphs();
        assertTrue(graphs.size() == 1);
        Node node = model.addNode();
        graphs = model.getGraphs();
        assertTrue(graphs.contains(node.getGraph()));
    }

    @Test public void testAddNode()
    {
        Model model = new Model();
        Node node = model.addNode();
        assertTrue(node.getGraph().getModel() == model);
        node = model.addNode();
        assertTrue(node.getGraph().getModel() == model);
        node = model.addNode();
        assertTrue(node.getGraph().getModel() == model);
    }
}
