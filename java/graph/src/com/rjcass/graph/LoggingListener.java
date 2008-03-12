package com.rjcass.graph;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggingListener implements ModelEntityFactoryListener,
        ModelListener, GraphListener, NodeListener, ArcListener
{
    private static Log sLog = LogFactory.getLog(LoggingListener.class);

    public LoggingListener()
    {
    }

    @Override public void modelCreated(Model model)
    {
        sLog.info("ModelEntityFactory.modelCreated(model:" + model + ")");
        model.addListener(this);
    }

    @Override public void graphCreated(Graph graph)
    {
        sLog.info("ModelEntityFactory.graphCreated(graph:" + graph + ")");
        graph.addListener(this);
    }

    @Override public void nodeCreated(Node node)
    {
        sLog.info("ModelEntityFactory.nodeCreated(node:" + node + ")");
        node.addListener(this);
    }

    @Override public void arcCreated(Arc arc)
    {
        sLog.info("ModelEntityFactory.arcCreated(arc:" + arc + ")");
        arc.addListener(this);
    }

    @Override public void graphAdded(Model model, Graph graph)
    {
        sLog.info("Model.graphAdded(model:" + model + ",graph:" + graph + ")");
    }

    @Override public void graphRemoved(Model model, Graph graph)
    {
        sLog.info("Model.graphAdded(model:" + model + ",graph:" + graph + ")");
    }

    @Override public void modelSet(Graph graph, Model oldModel, Model newModel)
    {
        sLog.info("Graph.modelSet(graph:" + graph + ",oldModel:" + oldModel
                + ",newModel:" + newModel + ")");
    }

    @Override public void nodeAdded(Graph graph, Node node)
    {
        sLog.info("Graph.nodeAdded(graph:" + graph + ",node:" + node + ")");
    }

    @Override public void nodeRemoved(Graph graph, Node node)
    {
        sLog.info("Graph.nodeRemoved(graph:" + graph + ",node:" + node + ")");
    }

    @Override public void removed(Graph graph)
    {
        sLog.info("Graph.removed(graph:" + graph + ")");
    }

    @Override public void directedSet(Arc arc, boolean directed)
    {
        sLog.info("Arc.directedSet(arc:" + arc + ",directed:" + directed + ")");
    }

    @Override public void endNodeSet(Arc arc, Node oldNode, Node newNode)
    {
        sLog.info("Arc.endNodeSet(arc:" + arc + ",oldNode:" + oldNode
                + ",newNode:" + newNode + ")");
    }

    @Override public void removed(Arc arc)
    {
        sLog.info("Arc.removed(arc:" + arc + ")");
    }

    @Override public void reversed(Arc arc)
    {
        sLog.info("Arc.reversed(arc:" + arc + ")");
    }

    @Override public void startNodeSet(Arc arc, Node oldNode, Node newNode)
    {
        sLog.info("Arc.startNodeSet(arc:" + arc + ",oldNode:" + oldNode
                + ",newNode:" + newNode + ")");
    }

    @Override public void arcAdded(Node node, Arc arc)
    {
        sLog.info("Node.arcAdded(node:" + node + ",arc:" + arc + ")");
    }

    @Override public void arcRemoved(Node node, Arc arc)
    {
        sLog.info("Node.arcRemoved(node:" + node + ",arc:" + arc + ")");
    }

    @Override public void graphSet(Node node, Graph oldGraph, Graph newGraph)
    {
        sLog.info("Node.graphSet(node:" + node + ",oldGraph:" + oldGraph
                + ",newGraph" + newGraph + ")");
    }

    @Override public void removed(Node node)
    {
        sLog.info("Node.removed(node:" + node + ")");
    }
}
