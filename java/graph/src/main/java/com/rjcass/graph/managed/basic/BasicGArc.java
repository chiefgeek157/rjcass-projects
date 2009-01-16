package com.rjcass.graph.managed.basic;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.commons.attribute.AbstractAttributeContainer;
import com.rjcass.graph.GArcListener;
import com.rjcass.graph.GGraph;
import com.rjcass.graph.GNode;
import com.rjcass.graph.managed.ManagedGArc;
import com.rjcass.graph.managed.ManagedGGraph;
import com.rjcass.graph.managed.ManagedGNode;

public class BasicGArc extends AbstractAttributeContainer implements ManagedGArc
{
	private static Log sLog = LogFactory.getLog(BasicGArc.class);

	private String mId;
	private String mName;

	private ManagedGGraph mGraph;
	private ManagedGNode mStartNode;
	private ManagedGNode mEndNode;
	private boolean mDirected;
	private Set<GArcListener> mListeners;

	public BasicGArc()
	{
		mListeners = new HashSet<GArcListener>();
	}

	public boolean isValid()
	{
		return (mGraph != null && doIsValid());
	}

	public void setUndirected()
	{
		validate();
		doSetUndirected();
	}

	public void setDirection(GNode source, Direction direction)
	{
		validate();
		doSetDirection(source, direction);
	}

	public void reverse()
	{
		validate();
		doReverse();
	}

	public void remove()
	{
		validate();

		// Delegate to Model
		mGraph.getManagedModel().removeManagedArc(this);

		removeNotifyOnly();
	}

	public String getId()
	{
		return mId;
	}

	public void setName(String name)
	{
		mName = name;
	}

	public String getName()
	{
		return mName;
	}

	public GGraph getGraph()
	{
		return mGraph;
	}

	public boolean isConnectedTo(GNode node)
	{
		validate();
		return (node == mStartNode || node == mEndNode);
	}

	public GNode getStartNode()
	{
		validate();
		return mStartNode;
	}

	public GNode getEndNode()
	{
		validate();
		return mEndNode;
	}

	public GNode getOtherNode(GNode node)
	{
		validate();
		return getOtherManagedNode((ManagedGNode)node);
	}

	public boolean isDirected()
	{
		validate();
		return mDirected;
	}

	public Direction getDirection(GNode node)
	{
		validate();
		if (mStartNode != node && mEndNode != node)
			throw new IllegalArgumentException("Node is not part of this Arc");

		Direction direction = Direction.UNDIRECTED;
		if (mDirected)
		{
			if (node == mStartNode)
				direction = Direction.OUTBOUND;
			else
				direction = Direction.INBOUND;
		}
		return direction;
	}

	public void addListener(GArcListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(GArcListener listener)
	{
		mListeners.remove(listener);
	}

	public void setId(String id)
	{
		mId = id;
	}

	public void setManagedGraph(ManagedGGraph graph)
	{
		ManagedGGraph oldGraph = mGraph;
		mGraph = graph;
		fireGraphSet(oldGraph, mGraph);
	}

	public void setManagedNodes(ManagedGNode startNode, ManagedGNode endNode)
	{
		setManagedNodes(startNode, endNode, true);
	}

	public void setManagedNodes(ManagedGNode startNode, ManagedGNode endNode, boolean undirected)
	{
		if (startNode != mStartNode || endNode != mEndNode)
		{
			ManagedGNode oldStartNode = mStartNode;
			ManagedGNode oldEndNode = mEndNode;
			mStartNode = startNode;
			mEndNode = endNode;
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
			doSetUndirected();
		else
			doSetDirection(mStartNode, Direction.OUTBOUND);
	}

	public void removeNotifyOnly()
	{
		if (mStartNode != null)
			mStartNode.removeManagedArc(this);
		if (mEndNode != null)
			mEndNode.removeManagedArc(this);

		fireRemoved();
	}

	public ManagedGNode getStartManagedNode()
	{
		return mStartNode;
	}

	public ManagedGNode getEndManagedNode()
	{
		return mEndNode;
	}

	public ManagedGNode getOtherManagedNode(ManagedGNode node)
	{
		ManagedGNode otherNode;
		if (node == mStartNode)
			otherNode = mEndNode;
		else if (node == mEndNode)
			otherNode = mStartNode;
		else
			throw new IllegalArgumentException("Node is not on Arc");
		return otherNode;
	}

	@Override
	public String toString()
	{
		return "BasicArc[" + mId + "]";
	}

	protected void validate()
	{
		if (!isValid())
			throw new IllegalStateException();
	}

	protected boolean doIsValid()
	{
		return true;
	}

	private void fireGraphSet(ManagedGGraph oldGraph, ManagedGGraph newGraph)
	{
		sLog.debug("Firing " + this + ".GraphSet(oldGraph:" + oldGraph + ",newGraph:" + newGraph + ")");
		Set<GArcListener> listeners = new HashSet<GArcListener>(mListeners);
		for (GArcListener listener : listeners)
			listener.graphSet(this, oldGraph, newGraph);
	}

	private void fireNodesSet(ManagedGNode oldStartNode, ManagedGNode oldEndNode, ManagedGNode newStartNode, ManagedGNode newEndNode)
	{
		sLog.debug("Firing " + this + ".NodesSet(oldStartNode:" + oldStartNode + ",oldEndNode:" + oldEndNode + ",newStartNode:"
				+ newStartNode + ",newEndNode:" + newEndNode + ")");
		Set<GArcListener> listeners = new HashSet<GArcListener>(mListeners);
		for (GArcListener listener : listeners)
			listener.nodesSet(this, oldStartNode, oldEndNode, newStartNode, newEndNode);
	}

	private void fireDirectedSet(boolean directed)
	{
		sLog.debug("Firing " + this + ".DirectedSet(directed:" + directed + ")");
		Set<GArcListener> listeners = new HashSet<GArcListener>(mListeners);
		for (GArcListener listener : listeners)
			listener.directedSet(this, directed);
	}

	private void fireReversed()
	{
		sLog.debug("Firing " + this + ".Revered()");
		Set<GArcListener> listeners = new HashSet<GArcListener>(mListeners);
		for (GArcListener listener : listeners)
			listener.reversed(this);
	}

	private void fireRemoved()
	{
		sLog.debug("Firing " + this + ".Removed()");
		Set<GArcListener> listeners = new HashSet<GArcListener>(mListeners);
		for (GArcListener listener : listeners)
			listener.removed(this);
	}

	private void doSetUndirected()
	{
		if (mDirected)
		{
			mDirected = false;
			fireDirectedSet(mDirected);
		}
	}

	private void doSetDirection(GNode source, Direction direction)
	{
		if (!isConnectedTo(source))
			throw new IllegalArgumentException("Arc not connected to Node");

		if (direction == Direction.UNDIRECTED)
		{
			doSetUndirected();
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
				doReverse();
			}
		}
	}

	private void doReverse()
	{
		if (!mDirected)
			throw new IllegalStateException("Cannot reverse an undirected Arc");

		ManagedGNode temp = mStartNode;
		mStartNode = mEndNode;
		mEndNode = temp;
		fireReversed();
	}
}
