package com.rjcass.graph;

import java.util.HashSet;
import java.util.Set;

public class Node extends ModelEntity
{
    private Graph mGraph;
    private Set<Arc> mArcs;
    private Set<NodeListener> mListeners;

    @Override public boolean isValid()
    {
        return(mGraph != null && mGraph.isValid() && doIsValid());
    }

    public Graph getGraph()
    {
        validate();
        return mGraph;
    }

    public Arc joinTo(Node node)
    {
        validate();
        if(!node.isValid()) throw new IllegalArgumentException("Invalid node");
        if(getGraph().getModel() != node.getGraph().getModel())
            throw new IllegalArgumentException(
                    "Node must be part of this Model");
        if(this == node)
            throw new IllegalArgumentException("Cannot join to self");
        if(isAdjacentTo(node))
            throw new IllegalArgumentException("Already connected to node");

        joinToBefore(node);
        Arc arc = getGraph().getModel().createArc(this, node);
        joinToAfter(arc);

        return arc;
    }

    public void disconnectFrom(Node node)
    {
        validate();
        if(!node.isValid()) throw new IllegalArgumentException("Invalid node");
        Arc arc = getAdjacentArc(node);
        if(arc == null)
            throw new IllegalArgumentException("Not connected to node");
        disconnectFromBefore(arc);
        arc.remove();
        disconnectFromAfter(arc);
    }

    public void remove()
    {
        validate();
        removeBefore();
        for(Node node : getAdjacentNodes())
        {
            disconnectFrom(node);
        }
        mGraph.removeNode(this);
        removeAfter();
        fireRemoved();
    }

    public Set<Node> getAdjacentNodes()
    {
        return getAdjacentNodes(null);
    }

    public Set<Node> getAdjacentNodes(AdjacencyFilter filter)
    {
        validate();
        Set<Node> nodes = new HashSet<Node>();
        for(Arc arc : mArcs)
        {
            Node node = arc.getOtherNode(this);
            if(filter == null || filter != null && filter.passes(arc, node))
                nodes.add(arc.getOtherNode(this));
        }
        return nodes;
    }

    public boolean isAdjacentTo(Node node)
    {
        return(getAdjacentArc(node) != null);
    }

    public Arc getAdjacentArc(Node node)
    {
        validate();
        Arc result = null;
        if(node != null && getGraph() == node.getGraph())
        {
            for(Arc arc : mArcs)
            {
                if(arc.getOtherNode(this) == node)
                {
                    result = arc;
                    break;
                }
            }
        }
        return result;
    }

    public void addListener(NodeListener listener)
    {
        mListeners.add(listener);
    }

    public void removeListener(NodeListener listener)
    {
        mListeners.remove(listener);
    }

    protected Node()
    {
        mArcs = new HashSet<Arc>();
        mListeners = new HashSet<NodeListener>();
    }

    void setGraph(Graph graph)
    {
        if(graph != mGraph)
        {
            Graph oldGraph = mGraph;
            mGraph = graph;
            fireGraphSet(oldGraph, graph);
        }
    }

    void addArc(Arc arc)
    {
        if(mArcs.add(arc)) fireArcAdded(arc);
    }

    void removeArc(Arc arc)
    {
        if(mArcs.remove(arc)) fireArcRemoved(arc);
    }

    protected boolean doIsValid()
    {
        return true;
    }

    protected void joinToBefore(Node node)
    {
    }

    protected void joinToAfter(Arc arc)
    {
    }

    protected void disconnectFromBefore(Arc arc)
    {
    }

    protected void disconnectFromAfter(Arc arc)
    {
    }

    protected void removeBefore()
    {
    }

    protected void removeAfter()
    {
    }

    private void fireGraphSet(Graph oldGraph, Graph newGraph)
    {
        Set<NodeListener> listeners = new HashSet<NodeListener>(mListeners);
        for(NodeListener listener : listeners)
            listener.graphSet(this, oldGraph, newGraph);
    }

    private void fireArcAdded(Arc arc)
    {
        Set<NodeListener> listeners = new HashSet<NodeListener>(mListeners);
        for(NodeListener listener : listeners)
            listener.arcAdded(this, arc);
    }

    private void fireArcRemoved(Arc arc)
    {
        Set<NodeListener> listeners = new HashSet<NodeListener>(mListeners);
        for(NodeListener listener : listeners)
            listener.arcRemoved(this, arc);
    }

    private void fireRemoved()
    {
        Set<NodeListener> listeners = new HashSet<NodeListener>(mListeners);
        for(NodeListener listener : listeners)
            listener.removed(this);
    }
}
