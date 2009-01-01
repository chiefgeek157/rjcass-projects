package com.rjcass.graph.basic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.graph.AdjacencyFilter;
import com.rjcass.graph.Arc;
import com.rjcass.graph.ArcFilter;
import com.rjcass.graph.Graph;
import com.rjcass.graph.Node;
import com.rjcass.graph.NodeFilter;
import com.rjcass.graph.NodeListener;
import com.rjcass.graph.filter.DirectedArcFilter;
import com.rjcass.graph.managed.ManagedArc;
import com.rjcass.graph.managed.ManagedGraph;
import com.rjcass.graph.managed.ManagedNode;

public class BasicNode implements ManagedNode
{
	private static Log sLog = LogFactory.getLog(BasicNode.class);

	private String mId;
	private ManagedGraph mGraph;
	private Set<ManagedArc> mArcs;
	private Set<NodeListener> mListeners;

	public BasicNode()
	{
		mArcs = new HashSet<ManagedArc>();
		mListeners = new HashSet<NodeListener>();
	}

	public boolean isValid()
	{
		return (mGraph != null && doIsValid());
	}

	public String getId()
	{
		validate();
		return mId;
	}

	public Graph getGraph()
	{
		validate();
		return getManagedGraph();
	}

	public Arc joinTo(String arcId, Node node)
	{
		return joinTo(arcId, node, false);
	}

	public Arc joinTo(String arcId, Node node, boolean directed)
	{
		validate();

		if (!node.isValid())
			throw new IllegalArgumentException("Invalid node");
		if (getGraph().getModel() != node.getGraph().getModel())
			throw new IllegalArgumentException("Nodes must be part of the same Model");
		if (this == node)
			throw new IllegalArgumentException("Cannot join Node to itself");
		if (isAdjacentTo(node))
			throw new IllegalArgumentException("Already connected to given Node");

		// Delegate Arc creation to the Model
		ManagedArc arc = getManagedGraph().getManagedModel().addManagedArc(arcId, this, (ManagedNode)node, directed);

		return arc;
	}

	public void disconnectFrom(Node node)
	{
		validate();

		Arc arc = getConnectingArc(node);
		if (arc == null)
			throw new IllegalArgumentException("Not connected to node");

		// Delegate removal to the Arc
		arc.remove();
	}

	public void remove()
	{
		validate();

		Set<ManagedArc> arcs = new HashSet<ManagedArc>(mArcs);
		for (ManagedArc arc : arcs)
			arc.remove();

		mGraph.removeManagedNode(this);

		removeNotifyOnly();
	}

	public Arc getConnectingArc(Node node)
	{
		validate();
		Arc result = null;
		if (node != null && getGraph() == node.getGraph())
		{
			for (Arc arc : mArcs)
			{
				if (arc.getOtherNode(this) == node)
				{
					result = arc;
					break;
				}
			}
		}
		return result;
	}

	public Set<? extends Arc> getArcs()
	{
		validate();
		return getManagedArcs();
	}

	public Set<? extends Arc> getInboundArcs()
	{
		return getArcs(new DirectedArcFilter(this, Arc.Direction.INBOUND));
	}

	public Set<? extends Arc> getOutboundArcs()
	{
		return getArcs(new DirectedArcFilter(this, Arc.Direction.OUTBOUND));
	}

	public Set<? extends Arc> getArcs(ArcFilter filter)
	{
		validate();
		Set<ManagedArc> arcs = new HashSet<ManagedArc>();
		for (ManagedArc arc : mArcs)
			if (filter.passes(arc))
				arcs.add(arc);
		return Collections.unmodifiableSet(arcs);
	}

	public Set<? extends Arc> getArcs(NodeFilter filter)
	{
		validate();
		Set<ManagedArc> arcs = new HashSet<ManagedArc>();
		for (ManagedArc arc : mArcs)
			if (filter.passes(arc.getOtherNode(this)))
				arcs.add(arc);
		return Collections.unmodifiableSet(arcs);
	}

	public Set<? extends Arc> getArcs(AdjacencyFilter filter)
	{
		validate();
		Set<ManagedArc> arcs = new HashSet<ManagedArc>();
		for (ManagedArc arc : mArcs)
			if (filter.passes(arc, arc.getOtherNode(this)))
				arcs.add(arc);
		return Collections.unmodifiableSet(arcs);
	}

	public boolean isAdjacentTo(Node node)
	{
		validate();
		return (getConnectingArc(node) != null);
	}

	public Set<? extends Node> getAdjacentNodes()
	{
		validate();
		return getAdjacentManagedNodes();
	}

	public Set<? extends Node> getInboundNodes()
	{
		return getAdjacentNodes(new DirectedArcFilter(this, Arc.Direction.INBOUND));
	}

	public Set<? extends Node> getOutboundNodes()
	{
		return getAdjacentNodes(new DirectedArcFilter(this, Arc.Direction.OUTBOUND));
	}

	public Set<? extends Node> getAdjacentNodes(ArcFilter filter)
	{
		validate();
		Set<ManagedNode> nodes = new HashSet<ManagedNode>();
		for (ManagedArc arc : mArcs)
			if (filter.passes(arc))
				nodes.add(arc.getOtherManagedNode(this));
		return Collections.unmodifiableSet(nodes);
	}

	public Set<? extends Node> getAdjacentNodes(NodeFilter filter)
	{
		validate();
		Set<ManagedNode> nodes = new HashSet<ManagedNode>();
		for (ManagedArc arc : mArcs)
		{
			ManagedNode otherNode = arc.getOtherManagedNode(this);
			if (filter.passes(otherNode))
				nodes.add(otherNode);
		}
		return Collections.unmodifiableSet(nodes);
	}

	public Set<? extends Node> getAdjacentNodes(AdjacencyFilter filter)
	{
		validate();
		Set<ManagedNode> nodes = new HashSet<ManagedNode>();
		for (ManagedArc arc : mArcs)
		{
			ManagedNode otherNode = arc.getOtherManagedNode(this);
			if (filter.passes(arc, otherNode))
				nodes.add(otherNode);
		}
		return Collections.unmodifiableSet(nodes);
	}

	public void addListener(NodeListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(NodeListener listener)
	{
		mListeners.remove(listener);
	}

	public void setId(String id)
	{
		mId = id;
	}

	public void setManagedGraph(ManagedGraph graph)
	{
		if (graph != mGraph)
		{
			ManagedGraph oldGraph = mGraph;
			mGraph = graph;
			fireGraphSet(oldGraph, graph);
		}
	}

	public void addManagedArc(ManagedArc arc)
	{
		mArcs.add(arc);
		fireArcAdded(arc);
	}

	public void removeManagedArc(ManagedArc arc)
	{
		mArcs.remove(arc);
		fireArcRemoved(arc);
	}

	public void removeNotifyOnly()
	{
		fireRemoved();
	}

	public ManagedGraph getManagedGraph()
	{
		return mGraph;
	}

	public Set<? extends ManagedArc> getManagedArcs()
	{
		return Collections.unmodifiableSet(new HashSet<ManagedArc>(mArcs));
	}

	public Set<? extends ManagedNode> getAdjacentManagedNodes()
	{
		Set<ManagedNode> nodes = new HashSet<ManagedNode>();
		for (ManagedArc arc : mArcs)
			nodes.add(arc.getOtherManagedNode(this));
		return Collections.unmodifiableSet(nodes);
	}

	@Override
	public String toString()
	{
		return "BasicNode[" + mId + "]";
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
		Set<NodeListener> listeners = new HashSet<NodeListener>(mListeners);
		for (NodeListener listener : listeners)
			listener.graphSet(this, oldGraph, newGraph);
	}

	private void fireArcAdded(Arc arc)
	{
		sLog.debug("Firing " + this + ".ArcAdded(arc:" + arc + ")");
		Set<NodeListener> listeners = new HashSet<NodeListener>(mListeners);
		for (NodeListener listener : listeners)
			listener.arcAdded(this, arc);
	}

	private void fireArcRemoved(Arc arc)
	{
		sLog.debug("Firing " + this + ".ArcRemoved(arc:" + arc + ")");
		Set<NodeListener> listeners = new HashSet<NodeListener>(mListeners);
		for (NodeListener listener : listeners)
			listener.arcRemoved(this, arc);
	}

	private void fireRemoved()
	{
		sLog.debug("Firing " + this + ".Removed()");
		Set<NodeListener> listeners = new HashSet<NodeListener>(mListeners);
		for (NodeListener listener : listeners)
			listener.removed(this);
	}
}
