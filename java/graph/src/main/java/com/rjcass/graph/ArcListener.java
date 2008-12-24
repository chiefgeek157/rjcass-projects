package com.rjcass.graph;

public interface ArcListener
{
    void directedSet(Arc arc, boolean directed);
    void reversed(Arc arc);
    void startNodeSet(Arc arc, Node oldNode, Node newNode);
    void endNodeSet(Arc arc, Node oldNode, Node newNode);
    void removed(Arc arc);
}
