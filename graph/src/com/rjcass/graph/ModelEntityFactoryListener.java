package com.rjcass.graph;

public interface ModelEntityFactoryListener
{
    void modelCreated(Model model);

    void graphCreated(Graph graph);

    void nodeCreated(Node node);

    void arcCreated(Arc arc);
}
