package com.rjcass.graph.basic;

import java.util.HashSet;
import java.util.Set;

import com.rjcass.graph.ArcListener;
import com.rjcass.graph.Node;
import com.rjcass.graph.basic.manage.ManagedArc;

public class BasicArc extends AbstractModelEntity implements ManagedArc
{
    private boolean mDirected;
    private Node mStartNode;
    private Node mEndNode;
    private Set<ArcListener> mListeners;

    @Override public boolean isValid()
    {
        return(mStartNode != null && mEndNode != null && mStartNode.isValid() && mEndNode
                .isValid());
    }

    public boolean isDirected()
    {
        validate();
        return mDirected;
    }

    public void setDirected(boolean directed)
    {
        validate();
        if(directed != mDirected)
        {
            mDirected = directed;
            fireDirectedSet(directed);
        }
    }

    public Node getStartNode()
    {
        validate();
        return mStartNode;
    }

    public Node getEndNode()
    {
        validate();
        return mEndNode;
    }

    public void reverse()
    {
        validate();
        if(!mDirected)
            throw new IllegalStateException("Cannot reverse an undirected Arc");

        Node temp = mStartNode;
        mStartNode = mEndNode;
        mEndNode = temp;
        fireReversed();
    }

    public Node getOtherNode(Node node)
    {
        validate();
        Node otherNode;
        if(node == mStartNode)
            otherNode = mEndNode;
        else if(node == mEndNode)
            otherNode = mStartNode;
        else
            throw new IllegalArgumentException("Node is not on Arc");
        return otherNode;
    }

    public void remove()
    {
        validate();
        mStartNode.removeArc(this);
        mEndNode.removeArc(this);
        mStartNode.getGraph().getModel().deleteArc(mStartNode, mEndNode);
        mStartNode = null;
        mEndNode = null;
        fireRemoved();
    }

    public void addListener(ArcListener listener)
    {
        mListeners.add(listener);
    }

    public void removeListener(ArcListener listener)
    {
        mListeners.remove(listener);
    }

    BasicArc()
    {
        mListeners = new HashSet<ArcListener>();
    }

    void setStartNode(Node node)
    {
        if(node != mStartNode)
        {
            node.addArc(this);
            Node oldNode = mStartNode;
            mStartNode = node;
            fireStartNodeSet(oldNode, node);
        }
    }

    void setEndNode(Node node)
    {
        if(node != mEndNode)
        {
            node.addArc(this);
            Node oldNode = mEndNode;
            mEndNode = node;
            fireEndNodeSet(oldNode, node);
        }
    }

    private void fireDirectedSet(boolean directed)
    {
        Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
        for(ArcListener listener : listeners)
            listener.directedSet(this, directed);
    }

    private void fireReversed()
    {
        Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
        for(ArcListener listener : listeners)
            listener.reversed(this);
    }

    private void fireStartNodeSet(Node oldNode, Node newNode)
    {
        Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
        for(ArcListener listener : listeners)
            listener.startNodeSet(this, oldNode, newNode);
    }

    private void fireEndNodeSet(Node oldNode, Node newNode)
    {
        Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
        for(ArcListener listener : listeners)
            listener.endNodeSet(this, oldNode, newNode);
    }

    private void fireRemoved()
    {
        Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
        for(ArcListener listener : listeners)
            listener.removed(this);
    }
}
