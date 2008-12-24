package com.rjcass.graph;

public interface GraphListener
{
    void removed(Graph graph);

    void modelSet(Graph graph, Model oldModel, Model newModel);

    void nodeAdded(Graph graph, Node node);

    void nodeRemoved(Graph graph, Node node);
}
