package com.rjcass.graph;

import com.rjcass.graph.basic.BasicGraph;
import com.rjcass.graph.basic.BasicModel;

public interface GraphListener
{
    void removed(BasicGraph graph);

    void modelSet(BasicGraph graph, BasicModel oldModel, BasicModel newModel);

    void nodeAdded(BasicGraph graph, Node node);

    void nodeRemoved(BasicGraph graph, Node node);
}
