package com.rjcass.graph.basic;

import java.util.HashSet;
import java.util.Set;

import com.rjcass.graph.AbstractModelEntity;
import com.rjcass.graph.ArcListener;
import com.rjcass.graph.Node;
import com.rjcass.graph.managed.ManagedArc;
import com.rjcass.graph.managed.ManagedModel;
import com.rjcass.graph.managed.ManagedNode;

public class BasicArc extends AbstractModelEntity implements ManagedArc
{
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

		ManagedModel model = mStartNode.getManagedGraph().getManagedModel();

		mStartNode.removeArc(this);
		mEndNode.removeArc(this);
		model.removeArc(this);
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

	public void setStartNode(ManagedNode node)
	{
		if (node != mStartNode)
		{
			node.addArc(this);
			ManagedNode oldNode = mStartNode;
			mStartNode = node;
			fireStartNodeSet(oldNode, node);
		}
	}

	public void setEndNode(ManagedNode node)
	{
		if (node != mEndNode)
		{
			node.addArc(this);
			ManagedNode oldNode = mEndNode;
			mEndNode = node;
			fireEndNodeSet(oldNode, node);
		}
	}

	protected boolean doIsValid()
	{
		return true;
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

	private void fireStartNodeSet(ManagedNode oldNode, ManagedNode newNode)
	{
		Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
		for (ArcListener listener : listeners)
			listener.startNodeSet(this, oldNode, newNode);
	}

	private void fireEndNodeSet(ManagedNode oldNode, ManagedNode newNode)
	{
		Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
		for (ArcListener listener : listeners)
			listener.endNodeSet(this, oldNode, newNode);
	}

	private void fireRemoved()
	{
		Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
		for (ArcListener listener : listeners)
			listener.removed(this);
	}
}
