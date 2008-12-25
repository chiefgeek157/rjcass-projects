package com.rjcass.graph;

import static org.junit.Assert.assertFalse;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.rjcass.graph.basic.BasicGraph;
import com.rjcass.graph.basic.BasicModel;
import com.rjcass.graph.basic.Node;

public class GraphTest
{
    private BasicModel mBasicModel;
    private Node mNode;

    @Before public void setUp()
    {
        mBasicModel = new BasicModel();
        mNode = mBasicModel.addNode();
    }

    @Test public void testRemove()
    {
        BasicGraph graph = mNode.getGraph();
        graph.remove();
        assertFalse(graph.isValid());
        Set<BasicGraph> graphs = mBasicModel.getGraphs();
        assertFalse(graphs.contains(graph));
    }
}
