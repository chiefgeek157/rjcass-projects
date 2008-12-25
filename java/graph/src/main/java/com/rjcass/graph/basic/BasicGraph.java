package com.rjcass.graph.basic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.rjcass.graph.GraphListener;
import com.rjcass.graph.Node;
import com.rjcass.graph.basic.manage.ManagedGraph;

public class BasicGraph extends AbstractModelEntity implements ManagedGraph
{
    private BasicModel mBasicModel;
    private Set<Node> mNodes;
    private Set<GraphListener> mListeners;

    @Override public boolean isValid()
    {
        return(mBasicModel != null && mBasicModel.isValid());
    }

    public BasicModel getModel()
    {
        validate();
        return mBasicModel;
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
        mBasicModel.removeGraph(this);
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

    BasicGraph()
    {
        mNodes = new HashSet<Node>();
        mListeners = new HashSet<GraphListener>();
    }

    void setModel(BasicModel basicModel)
    {
        if(basicModel == null && mBasicModel != null || basicModel != null && basicModel != mBasicModel)
        {
            BasicModel oldModel = mBasicModel;
            mBasicModel = basicModel;
            fireModelSet(oldModel, basicModel);
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
        if(mNodes.size() == 0) mBasicModel.removeGraph(this);
    }

    private void fireRemoved()
    {
        Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
        for(GraphListener listener : listeners)
            listener.removed(this);
    }

    private void fireModelSet(BasicModel oldModel, BasicModel newModel)
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
