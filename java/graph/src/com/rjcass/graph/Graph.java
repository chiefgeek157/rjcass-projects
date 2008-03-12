package com.rjcass.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Graph extends ModelEntity
{
    private Model mModel;
    private Set<Node> mNodes;
    private Set<GraphListener> mListeners;

    @Override public boolean isValid()
    {
        return(mModel != null && mModel.isValid());
    }

    public Model getModel()
    {
        validate();
        return mModel;
    }

    public int getNodeCount()
    {
        validate();
        return mNodes.size();
    }

    public Set<Node> getNodes()
    {
        validate();
        return Collections.unmodifiableSet(mNodes);
    }

    public void remove()
    {
        validate();
        mModel.removeGraph(this);
        fireRemoved();
    }

    public void addListener(GraphListener listener)
    {
        mListeners.add(listener);
    }

    public void removeListener(GraphListener listener)
    {
        mListeners.remove(listener);
    }

    Graph()
    {
        mNodes = new HashSet<Node>();
        mListeners = new HashSet<GraphListener>();
    }

    void setModel(Model model)
    {
        if(model == null && mModel != null || model != null && model != mModel)
        {
            Model oldModel = mModel;
            mModel = model;
            fireModelSet(oldModel, model);
        }
    }

    void addNode(Node node)
    {
        node.setGraph(this);
        mNodes.add(node);
        fireNodeAdded(node);
    }

    void removeNode(Node node)
    {
        node.setGraph(null);
        mNodes.remove(node);
        fireNodeRemoved(node);
        if(mNodes.size() == 0) mModel.removeGraph(this);
    }

    private void fireRemoved()
    {
        Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
        for(GraphListener listener : listeners)
            listener.removed(this);
    }

    private void fireModelSet(Model oldModel, Model newModel)
    {
        Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
        for(GraphListener listener : listeners)
            listener.modelSet(this, oldModel, newModel);
    }

    private void fireNodeAdded(Node node)
    {
        Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
        for(GraphListener listener : listeners)
            listener.nodeAdded(this, node);
    }

    private void fireNodeRemoved(Node node)
    {
        Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
        for(GraphListener listener : listeners)
            listener.nodeRemoved(this, node);
    }
}
