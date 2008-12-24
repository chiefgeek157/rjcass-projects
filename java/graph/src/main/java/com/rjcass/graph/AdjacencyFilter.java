package com.rjcass.graph;

public interface AdjacencyFilter
{
    boolean passes(Arc arc, Node node);
}
