package com.rjcass.graph;

import java.util.HashSet;
import java.util.Set;

public class ModelEntityFactory
{
    private Set<ModelEntityFactoryListener> mListeners;
    
    public ModelEntityFactory()
    {
        mListeners = new HashSet<ModelEntityFactoryListener>();
    }

    public Model createModel()
    {
        Model model = new Model();
        model.setFactory(this);
        fireModelCreated(model);
        return model;
    }

    public Graph createGraph()
    {
        Graph graph = new Graph();
        fireGraphCreated(graph);
        return graph;
    }

    public Node createNode()
    {
        Node node = new Node();
        fireNodeCreated(node);
        return node;
    }

    public Arc createArc()
    {
        Arc arc = new Arc();
        fireArcCreated(arc);
        return arc;
    }

    public void addListener(ModelEntityFactoryListener listener)
    {
        mListeners.add(listener);
    }

    public void removeListener(ModelEntityFactoryListener listener)
    {
        mListeners.remove(listener);
    }

    private void fireModelCreated(Model model)
    {
        Set<ModelEntityFactoryListener> listeners = new HashSet<ModelEntityFactoryListener>(mListeners);
        for(ModelEntityFactoryListener listener : listeners)
            listener.modelCreated(model);
    }

    private void fireGraphCreated(Graph graph)
    {
        Set<ModelEntityFactoryListener> listeners = new HashSet<ModelEntityFactoryListener>(mListeners);
        for(ModelEntityFactoryListener listener : listeners)
            listener.graphCreated(graph);
    }

    private void fireNodeCreated(Node node)
    {
        Set<ModelEntityFactoryListener> listeners = new HashSet<ModelEntityFactoryListener>(mListeners);
        for(ModelEntityFactoryListener listener : listeners)
            listener.nodeCreated(node);
    }

    private void fireArcCreated(Arc arc)
    {
        Set<ModelEntityFactoryListener> listeners = new HashSet<ModelEntityFactoryListener>(mListeners);
        for(ModelEntityFactoryListener listener : listeners)
            listener.arcCreated(arc);
    }
}
