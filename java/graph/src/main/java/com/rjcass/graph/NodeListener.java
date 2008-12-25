package com.rjcass.graph;

import com.rjcass.graph.basic.BasicGraph;

public interface NodeListener
{
    void graphSet(Node node, BasicGraph oldGraph, BasicGraph newGraph);
    void arcAdded(Node node, Arc arc);
    void arcRemoved(Node node, Arc arc);
    void removed(Node node);
}
