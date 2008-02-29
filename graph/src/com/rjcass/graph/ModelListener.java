package com.rjcass.graph;

public interface ModelListener
{
    void graphAdded(Model model, Graph graph);

    void graphRemoved(Model model, Graph graph);
}
