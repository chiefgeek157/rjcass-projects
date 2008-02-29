package com.rjcass.graph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

public class NodeTest
{
    @Test public void testJoinTo()
    {
        ModelEntityFactory factory = new ModelEntityFactory();
        Model model = factory.createModel();
        Node node1 = model.addNode();
        Node node2 = model.addNode();
        Node node3 = model.addNode();
        Graph graph1 = node1.getGraph();
        Graph graph2 = node2.getGraph();
        Graph graph3 = node3.getGraph();
        assertTrue(model.getGraphCount() == 3);
        node1.joinTo(node2);
        node2.joinTo(node3);
        node3.joinTo(node1);
        assertTrue(model.getGraphCount() == 1);
        assertTrue(graph1.isValid());
        assertFalse(graph2.isValid());
        assertFalse(graph3.isValid());
        assertTrue(graph1.getNodeCount() == 3);
    }

    @Test(expected = IllegalArgumentException.class) public void testJoinToFailToSelf()
    {
        ModelEntityFactory factory = new ModelEntityFactory();
        Model model = factory.createModel();
        Node node1 = model.addNode();
        node1.joinTo(node1);
    }

    @Test(expected = IllegalArgumentException.class) public void testJoinToFailToSameNode()
    {
        ModelEntityFactory factory = new ModelEntityFactory();
        Model model = factory.createModel();
        Node node1 = model.addNode();
        Node node2 = model.addNode();
        node1.joinTo(node2);
        node1.joinTo(node2);
    }

    @Test(expected = IllegalArgumentException.class) public void testJoinToFailInvalidNode()
    {
        ModelEntityFactory factory = new ModelEntityFactory();
        Model model = factory.createModel();
        Node node1 = model.addNode();
        Node node2 = model.addNode();
        node2.remove();
        node1.joinTo(node2);
    }

    @Test(expected = IllegalArgumentException.class) public void testJoinToFailWrongModel()
    {
        ModelEntityFactory factory = new ModelEntityFactory();
        Model model1 = factory.createModel();
        Node node1 = model1.addNode();
        Model model2 = factory.createModel();
        Node node2 = model2.addNode();
        node1.joinTo(node2);
    }

    @Test public void testDisconnectFrom()
    {
        ModelEntityFactory factory = new ModelEntityFactory();
        Model model = factory.createModel();
        Node node1 = model.addNode();
        assertTrue(model.getGraphCount() == 1);
        Node node2 = model.addNode();
        assertTrue(model.getGraphCount() == 2);
        Node node3 = model.addNode();
        assertTrue(model.getGraphCount() == 3);
        node1.joinTo(node2);
        assertTrue(model.getGraphCount() == 2);
        node2.joinTo(node3);
        assertTrue(model.getGraphCount() == 1);
        node3.joinTo(node1);
        assertTrue(model.getGraphCount() == 1);
        node1.disconnectFrom(node2);
        assertTrue(model.getGraphCount() == 1);
        node1.disconnectFrom(node3);
        assertTrue(model.getGraphCount() == 2);
    }

    @Test public void testRemove()
    {
        ModelEntityFactory factory = new ModelEntityFactory();
        Model model = factory.createModel();
        Node node1 = model.addNode();
        Node node2 = model.addNode();
        Node node3 = model.addNode();
        node1.joinTo(node2);
        node2.joinTo(node3);
        node3.joinTo(node1);
        node1.remove();
        assertTrue(model.getGraphCount() == 1);
        assertFalse(node1.isValid());
    }

    @Test public void testGetAdjacentNodes()
    {
        ModelEntityFactory factory = new ModelEntityFactory();
        Model model = factory.createModel();
        Node node1 = model.addNode();
        Node node2 = model.addNode();
        Node node3 = model.addNode();
        Node node4 = model.addNode();
        Node node5 = model.addNode();
        Node node6 = model.addNode();
        node1.joinTo(node2);
        node1.joinTo(node3);
        node1.joinTo(node4);
        node1.joinTo(node5);
        node1.joinTo(node6);
        Set<Node> nodes = node1.getAdjacentNodes();
        assertTrue(nodes.size() == 5);
        node2.remove();
        nodes = node1.getAdjacentNodes();
        assertTrue(nodes.size() == 4);
    }

    @Test public void testIsAdjacentTo()
    {
        ModelEntityFactory factory = new ModelEntityFactory();
        Model model = factory.createModel();
        Node node1 = model.addNode();
        Node node2 = model.addNode();
        Node node3 = model.addNode();
        Node node4 = model.addNode();
        node1.joinTo(node2);
        node2.joinTo(node3);
        node3.joinTo(node4);
        node4.joinTo(node1);
        assertTrue(node1.isAdjacentTo(node2));
        assertTrue(node1.isAdjacentTo(node4));
        assertFalse(node1.isAdjacentTo(node3));
    }

    @Test public void testGetAdjacentArc()
    {
        ModelEntityFactory factory = new ModelEntityFactory();
        Model model = factory.createModel();
        Node node1 = model.addNode();
        Node node2 = model.addNode();
        Node node3 = model.addNode();
        Node node4 = model.addNode();
        Arc arc1 = node1.joinTo(node2);
        node2.joinTo(node3);
        node3.joinTo(node4);
        Arc arc4 = node4.joinTo(node1);
        assertTrue(node1.getAdjacentArc(node2) == arc1);
        assertTrue(node1.getAdjacentArc(node4) == arc4);
    }

    @Test public void testGetAdjacentArcWithFilter()
    {
        ModelEntityFactory factory = new ModelEntityFactory();
        Model model = factory.createModel();
        Node node1 = model.addNode();
        Node node2 = model.addNode();
        Node node3 = model.addNode();
        Node node4 = model.addNode();
        Arc arc1 = node1.joinTo(node2);
        node2.joinTo(node3);
        node3.joinTo(node4);
        Arc arc4 = node4.joinTo(node1);
        assertTrue(node1.getAdjacentArc(node2) == arc1);
        assertTrue(node1.getAdjacentArc(node4) == arc4);
    }
}
