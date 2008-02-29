package com.rjcass.graph;

import static org.junit.Assert.assertFalse;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class GraphTest
{
    private Model mModel;
    private Node mNode;

    @Before public void setUp()
    {
        mModel = new Model();
        mNode = mModel.addNode();
    }

    @Test public void testRemove()
    {
        Graph graph = mNode.getGraph();
        graph.remove();
        assertFalse(graph.isValid());
        Set<Graph> graphs = mModel.getGraphs();
        assertFalse(graphs.contains(graph));
    }
}
