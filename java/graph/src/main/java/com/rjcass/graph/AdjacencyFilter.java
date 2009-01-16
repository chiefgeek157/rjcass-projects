package com.rjcass.graph;

public interface AdjacencyFilter
{
    boolean passes(GArc arc, GNode node);
}
