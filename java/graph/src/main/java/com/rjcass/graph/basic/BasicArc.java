package com.rjcass.graph.basic;

import java.util.HashSet;
import java.util.Set;

import com.rjcass.graph.AbstractModelEntity;
import com.rjcass.graph.ArcListener;
import com.rjcass.graph.Graph;
import com.rjcass.graph.Node;
import com.rjcass.graph.managed.ManagedArc;
import com.rjcass.graph.managed.ManagedGraph;
import com.rjcass.graph.managed.ManagedNode;

public class BasicArc extends AbstractModelEntity implements ManagedArc
{
	private ManagedGraph mGraph;
	private ManagedNode mStartNode;
	private ManagedNode mEndNode;
	private boolean mDirected;
	private Set<ArcListener> mListeners;

	public BasicArc()
	{
		mListeners = new HashSet<ArcListener>();
	}

	public boolean isValid()
	{
		return (mStartNode != null && mEndNode != null && mStartNode.isValid() && mEndNode.isValid() && doIsValid());
	}

	public void setNodes(Node startNode, Node endNode)
	{
		setNodes(startNode, endNode, true);
	}

	public void setNodes(Node startNode, Node endNode, boolean undirected)
	{
		if (startNode != mStartNode || endNode != mEndNode)
		{
			ManagedNode oldStartNode = mStartNode;
			ManagedNode oldEndNode = mEndNode;
			mStartNode = (ManagedNode)startNode;
			mEndNode = (ManagedNode)endNode;
			fireNodesSet(oldStartNode, oldEndNode, mStartNode, mEndNode);

			if (oldStartNode != mStartNode)
			{
				if (oldStartNode != null)
					oldStartNode.removeManagedArc(this);
				if (mStartNode != null)
					mStartNode.addManagedArc(this);
			}
			if (oldEndNode != mEndNode)
			{
				if (oldEndNode != null)
					oldEndNode.removeManagedArc(this);
				if (mEndNode != null)
					mEndNode.addManagedArc(this);
			}
		}
		if (undirected)
			setUndirected();
		else
			setDirection(mStartNode, Direction.OUTBOUND);
	}

	public void setUndirected()
	{
		validate();
		if (mDirected)
		{
			mDirected = false;
			fireDirectedSet(mDirected);
		}
	}

	public void setDirection(Node source, Direction direction)
	{
		validate();
		if (!isConnectedTo(source))
			throw new IllegalArgumentException("Arc not connected to Node");

		if (direction == Direction.UNDIRECTED)
		{
			setUndirected();
		}
		else
		{
			if (!mDirected)
			{
				mDirected = true;
				fireDirectedSet(mDirected);
			}
			else if (source == mStartNode && direction == Direction.INBOUND || source == mEndNode
					&& direction == Direction.OUTBOUND)
			{
				reverse();
			}
		}
	}

	public void reverse()
	{
		validate();
		if (!mDirected)
			throw new IllegalStateException("Cannot reverse an undirected Arc");

		ManagedNode temp = mStartNode;
		mStartNode = mEndNode;
		mEndNode = temp;
		fireReversed();
	}

	public void remove()
	{
		validate();

		// Delegate to Model
		mGraph.getManagedModel().removeManagedArc(this);

		removeNotifyOnly();
	}

	public Graph getGraph()
	{
		return mGraph;
	}

	public boolean isConnectedTo(Node node)
	{
		validate();
		return (node == mStartNode || node == mEndNode);
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

	public Node getOtherNode(Node node)
	{
		return getOtherManagedNode((ManagedNode)node);
	}

	public boolean isDirected()
	{
		validate();
		return mDirected;
	}

	public Direction getDirection(Node node)
	{
		validate();
		Direction direction = Direction.UNDIRECTED;
		if (mDirected)
		{
			if (node == mStartNode)
				direction = Direction.OUTBOUND;
			else if (node == mEndNode)
				direction = Direction.INBOUND;
			else
				throw new IllegalArgumentException("Node is not part of this Arc");
		}
		return direction;
	}

	public void addListener(ArcListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(ArcListener listener)
	{
		mListeners.remove(listener);
	}

	public void setManagedGraph(ManagedGraph graph)
	{
		ManagedGraph oldGraph = mGraph;
		mGraph = graph;
		fireGraphSet(oldGraph, mGraph);
	}

	public void removeNotifyOnly()
	{
		if (mStartNode != null)
			mStartNode.removeManagedArc(this);
		if (mEndNode != null)
			mEndNode.removeManagedArc(this);

		fireRemoved();
	}

	public ManagedNode getStartManagedNode()
	{
		return mStartNode;
	}

	public ManagedNode getEndManagedNode()
	{
		return mEndNode;
	}

	public ManagedNode getOtherManagedNode(ManagedNode node)
	{
		validate();
		ManagedNode otherNode;
		if (node == mStartNode)
			otherNode = mEndNode;
		else if (node == mEndNode)
			otherNode = mStartNode;
		else
			throw new IllegalArgumentException("Node is not on Arc");
		return otherNode;
	}

	protected boolean doIsValid()
	{
		return true;
	}

	private void fireGraphSet(ManagedGraph oldGraph, ManagedGraph newGraph)
	{
		Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
		for (ArcListener listener : listeners)
			listener.graphSet(this, oldGraph, newGraph);
	}

	private void fireNodesSet(ManagedNode oldStartNode, ManagedNode oldEndNode, ManagedNode newStartNode, ManagedNode newEndNode)
	{
		Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
		for (ArcListener listener : listeners)
			listener.nodesSet(this, oldStartNode, oldEndNode, newStartNode, newEndNode);
	}

	private void fireDirectedSet(boolean directed)
	{
		Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
		for (ArcListener listener : listeners)
			listener.directedSet(this, directed);
	}

	private void fireReversed()
	{
		Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
		for (ArcListener listener : listeners)
			listener.reversed(this);
	}

	private void fireRemoved()
	{
		Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
		for (ArcListener listener : listeners)
			listener.removed(this);
	}
}
