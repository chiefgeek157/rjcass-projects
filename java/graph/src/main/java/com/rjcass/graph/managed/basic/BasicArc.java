package com.rjcass.graph.managed.basic;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.commons.attribute.AbstractAttributeContainer;
import com.rjcass.graph.ArcListener;
import com.rjcass.graph.Graph;
import com.rjcass.graph.Node;
import com.rjcass.graph.managed.ManagedArc;
import com.rjcass.graph.managed.ManagedGraph;
import com.rjcass.graph.managed.ManagedNode;

public class BasicArc extends AbstractAttributeContainer implements ManagedArc
{
	private static Log sLog = LogFactory.getLog(BasicArc.class);

	private String mId;
	private String mName;

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
		return (mGraph != null && doIsValid());
	}

	public void setUndirected()
	{
		validate();
		doSetUndirected();
	}

	public void setDirection(Node source, Direction direction)
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
		validate();
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

	public void addListener(ArcListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(ArcListener listener)
	{
		mListeners.remove(listener);
	}

	public void setId(String id)
	{
		mId = id;
	}

	public void setManagedGraph(ManagedGraph graph)
	{
		ManagedGraph oldGraph = mGraph;
		mGraph = graph;
		fireGraphSet(oldGraph, mGraph);
	}

	public void setManagedNodes(ManagedNode startNode, ManagedNode endNode)
	{
		setManagedNodes(startNode, endNode, true);
	}

	public void setManagedNodes(ManagedNode startNode, ManagedNode endNode, boolean undirected)
	{
		if (startNode != mStartNode || endNode != mEndNode)
		{
			ManagedNode oldStartNode = mStartNode;
			ManagedNode oldEndNode = mEndNode;
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
		ManagedNode otherNode;
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

	private void fireGraphSet(ManagedGraph oldGraph, ManagedGraph newGraph)
	{
		sLog.debug("Firing " + this + ".GraphSet(oldGraph:" + oldGraph + ",newGraph:" + newGraph + ")");
		Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
		for (ArcListener listener : listeners)
			listener.graphSet(this, oldGraph, newGraph);
	}

	private void fireNodesSet(ManagedNode oldStartNode, ManagedNode oldEndNode, ManagedNode newStartNode, ManagedNode newEndNode)
	{
		sLog.debug("Firing " + this + ".NodesSet(oldStartNode:" + oldStartNode + ",oldEndNode:" + oldEndNode + ",newStartNode:"
				+ newStartNode + ",newEndNode:" + newEndNode + ")");
		Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
		for (ArcListener listener : listeners)
			listener.nodesSet(this, oldStartNode, oldEndNode, newStartNode, newEndNode);
	}

	private void fireDirectedSet(boolean directed)
	{
		sLog.debug("Firing " + this + ".DirectedSet(directed:" + directed + ")");
		Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
		for (ArcListener listener : listeners)
			listener.directedSet(this, directed);
	}

	private void fireReversed()
	{
		sLog.debug("Firing " + this + ".Revered()");
		Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
		for (ArcListener listener : listeners)
			listener.reversed(this);
	}

	private void fireRemoved()
	{
		sLog.debug("Firing " + this + ".Removed()");
		Set<ArcListener> listeners = new HashSet<ArcListener>(mListeners);
		for (ArcListener listener : listeners)
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

	private void doSetDirection(Node source, Direction direction)
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

		ManagedNode temp = mStartNode;
		mStartNode = mEndNode;
		mEndNode = temp;
		fireReversed();
	}
}
