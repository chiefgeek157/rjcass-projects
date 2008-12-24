package com.rjcass.graph;

public interface NodeListener
{
    void graphSet(Node node, Graph oldGraph, Graph newGraph);
    void arcAdded(Node node, Arc arc);
    void arcRemoved(Node node, Arc arc);
    void removed(Node node);
}
