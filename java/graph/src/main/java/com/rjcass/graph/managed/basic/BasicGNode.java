package com.rjcass.graph.managed.basic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.commons.attribute.AbstractAttributeContainer;
import com.rjcass.graph.AdjacencyFilter;
import com.rjcass.graph.GArc;
import com.rjcass.graph.GArcFilter;
import com.rjcass.graph.GGraph;
import com.rjcass.graph.GNode;
import com.rjcass.graph.GNodeFilter;
import com.rjcass.graph.GNodeListener;
import com.rjcass.graph.filter.DirectedGArcFilter;
import com.rjcass.graph.managed.ManagedGArc;
import com.rjcass.graph.managed.ManagedGGraph;
import com.rjcass.graph.managed.ManagedGNode;

public class BasicGNode extends AbstractAttributeContainer implements ManagedGNode
{
	private static Log sLog = LogFactory.getLog(BasicGNode.class);

	private String mId;
	private String mName;

	private ManagedGGraph mGraph;
	private Set<ManagedGArc> mArcs;
	private Set<GNodeListener> mListeners;

	public BasicGNode()
	{
		mArcs = new HashSet<ManagedGArc>();
		mListeners = new HashSet<GNodeListener>();
	}

	public boolean isValid()
	{
		return (mGraph != null && doIsValid());
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
		validate();
		return getManagedGraph();
	}

	public GArc joinTo(GNode node)
	{
		return joinTo(node, false);
	}

	public GArc joinTo(GNode node, boolean directed)
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
		ManagedGArc arc = getManagedGraph().getManagedModel().addManagedArc(this, (ManagedGNode)node, directed);

		return arc;
	}

	public void disconnectFrom(GNode node)
	{
		validate();

		GArc arc = getConnectingArc(node);
		if (arc == null)
			throw new IllegalArgumentException("Not connected to node");

		// Delegate removal to the Arc
		arc.remove();
	}

	public void remove()
	{
		validate();

		Set<ManagedGArc> arcs = new HashSet<ManagedGArc>(mArcs);
		for (ManagedGArc arc : arcs)
			arc.remove();

		mGraph.removeManagedNode(this);

		removeNotifyOnly();
	}

	public GArc getConnectingArc(GNode node)
	{
		validate();
		GArc result = null;
		if (node != null && getGraph() == node.getGraph())
		{
			for (GArc arc : mArcs)
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

	public Set<? extends GArc> getArcs()
	{
		validate();
		return getManagedArcs();
	}

	public Set<? extends GArc> getInboundArcs()
	{
		return getArcs(new DirectedGArcFilter(this, GArc.Direction.INBOUND));
	}

	public Set<? extends GArc> getOutboundArcs()
	{
		return getArcs(new DirectedGArcFilter(this, GArc.Direction.OUTBOUND));
	}

	public Set<? extends GArc> getArcs(GArcFilter filter)
	{
		validate();
		Set<ManagedGArc> arcs = new HashSet<ManagedGArc>();
		for (ManagedGArc arc : mArcs)
			if (filter.passes(arc))
				arcs.add(arc);
		return Collections.unmodifiableSet(arcs);
	}

	public Set<? extends GArc> getArcs(GNodeFilter filter)
	{
		validate();
		Set<ManagedGArc> arcs = new HashSet<ManagedGArc>();
		for (ManagedGArc arc : mArcs)
			if (filter.passes(arc.getOtherNode(this)))
				arcs.add(arc);
		return Collections.unmodifiableSet(arcs);
	}

	public Set<? extends GArc> getArcs(AdjacencyFilter filter)
	{
		validate();
		Set<ManagedGArc> arcs = new HashSet<ManagedGArc>();
		for (ManagedGArc arc : mArcs)
			if (filter.passes(arc, arc.getOtherNode(this)))
				arcs.add(arc);
		return Collections.unmodifiableSet(arcs);
	}

	public boolean isAdjacentTo(GNode node)
	{
		validate();
		return (getConnectingArc(node) != null);
	}

	public Set<? extends GNode> getAdjacentNodes()
	{
		validate();
		return getAdjacentManagedNodes();
	}

	public Set<? extends GNode> getInboundNodes()
	{
		return getAdjacentNodes(new DirectedGArcFilter(this, GArc.Direction.INBOUND));
	}

	public Set<? extends GNode> getOutboundNodes()
	{
		return getAdjacentNodes(new DirectedGArcFilter(this, GArc.Direction.OUTBOUND));
	}

	public Set<? extends GNode> getAdjacentNodes(GArcFilter filter)
	{
		validate();
		Set<ManagedGNode> nodes = new HashSet<ManagedGNode>();
		for (ManagedGArc arc : mArcs)
			if (filter.passes(arc))
				nodes.add(arc.getOtherManagedNode(this));
		return Collections.unmodifiableSet(nodes);
	}

	public Set<? extends GNode> getAdjacentNodes(GNodeFilter filter)
	{
		validate();
		Set<ManagedGNode> nodes = new HashSet<ManagedGNode>();
		for (ManagedGArc arc : mArcs)
		{
			ManagedGNode otherNode = arc.getOtherManagedNode(this);
			if (filter.passes(otherNode))
				nodes.add(otherNode);
		}
		return Collections.unmodifiableSet(nodes);
	}

	public Set<? extends GNode> getAdjacentNodes(AdjacencyFilter filter)
	{
		validate();
		Set<ManagedGNode> nodes = new HashSet<ManagedGNode>();
		for (ManagedGArc arc : mArcs)
		{
			ManagedGNode otherNode = arc.getOtherManagedNode(this);
			if (filter.passes(arc, otherNode))
				nodes.add(otherNode);
		}
		return Collections.unmodifiableSet(nodes);
	}

	public void addListener(GNodeListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(GNodeListener listener)
	{
		mListeners.remove(listener);
	}

	public void setId(String id)
	{
		mId = id;
	}

	public void setManagedGraph(ManagedGGraph graph)
	{
		if (graph != mGraph)
		{
			ManagedGGraph oldGraph = mGraph;
			mGraph = graph;
			fireGraphSet(oldGraph, graph);
		}
	}

	public void addManagedArc(ManagedGArc arc)
	{
		mArcs.add(arc);
		fireArcAdded(arc);
	}

	public void removeManagedArc(ManagedGArc arc)
	{
		mArcs.remove(arc);
		fireArcRemoved(arc);
	}

	public void removeNotifyOnly()
	{
		fireRemoved();
	}

	public ManagedGGraph getManagedGraph()
	{
		return mGraph;
	}

	public Set<? extends ManagedGArc> getManagedArcs()
	{
		return Collections.unmodifiableSet(new HashSet<ManagedGArc>(mArcs));
	}

	public Set<? extends ManagedGNode> getAdjacentManagedNodes()
	{
		Set<ManagedGNode> nodes = new HashSet<ManagedGNode>();
		for (ManagedGArc arc : mArcs)
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

	private void fireGraphSet(ManagedGGraph oldGraph, ManagedGGraph newGraph)
	{
		sLog.debug("Firing " + this + ".GraphSet(oldGraph:" + oldGraph + ",newGraph:" + newGraph + ")");
		Set<GNodeListener> listeners = new HashSet<GNodeListener>(mListeners);
		for (GNodeListener listener : listeners)
			listener.graphSet(this, oldGraph, newGraph);
	}

	private void fireArcAdded(GArc arc)
	{
		sLog.debug("Firing " + this + ".ArcAdded(arc:" + arc + ")");
		Set<GNodeListener> listeners = new HashSet<GNodeListener>(mListeners);
		for (GNodeListener listener : listeners)
			listener.arcAdded(this, arc);
	}

	private void fireArcRemoved(GArc arc)
	{
		sLog.debug("Firing " + this + ".ArcRemoved(arc:" + arc + ")");
		Set<GNodeListener> listeners = new HashSet<GNodeListener>(mListeners);
		for (GNodeListener listener : listeners)
			listener.arcRemoved(this, arc);
	}

	private void fireRemoved()
	{
		sLog.debug("Firing " + this + ".Removed()");
		Set<GNodeListener> listeners = new HashSet<GNodeListener>(mListeners);
		for (GNodeListener listener : listeners)
			listener.removed(this);
	}
}
