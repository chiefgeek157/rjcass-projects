package com.rjcass.graph.managed.basic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.commons.attribute.AbstractAttributeContainer;
import com.rjcass.graph.Arc;
import com.rjcass.graph.ArcFilter;
import com.rjcass.graph.GraphListener;
import com.rjcass.graph.Model;
import com.rjcass.graph.Node;
import com.rjcass.graph.NodeFilter;
import com.rjcass.graph.managed.ManagedArc;
import com.rjcass.graph.managed.ManagedGraph;
import com.rjcass.graph.managed.ManagedModel;
import com.rjcass.graph.managed.ManagedNode;

public class BasicGraph extends AbstractAttributeContainer implements ManagedGraph
{
	private static Log sLog = LogFactory.getLog(BasicGraph.class);

	private String mId;
	private String mName;
	private ManagedModel mModel;
	private Set<ManagedNode> mNodes;
	private Set<ManagedArc> mArcs;
	private Set<GraphListener> mListeners;

	public BasicGraph()
	{
		mNodes = new HashSet<ManagedNode>();
		mArcs = new HashSet<ManagedArc>();
		mListeners = new HashSet<GraphListener>();
	}

	public boolean isValid()
	{
		return (mModel != null && doIsValid());
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

	public Model getModel()
	{
		validate();
		return getManagedModel();
	}

	public Set<? extends Node> getNodes()
	{
		validate();
		return getManagedNodes();
	}

	public Set<? extends Node> getNodes(NodeFilter filter)
	{
		validate();
		Set<ManagedNode> nodes = new HashSet<ManagedNode>();
		for (ManagedNode node : mNodes)
		{
			if (filter.passes(node))
				nodes.add(node);
		}
		return Collections.unmodifiableSet(nodes);
	}

	public Set<? extends Arc> getArcs()
	{
		validate();
		return getManagedArcs();
	}

	public Set<? extends Arc> getArcs(ArcFilter filter)
	{
		validate();
		Set<ManagedArc> arcs = new HashSet<ManagedArc>();
		for (ManagedArc arc : mArcs)
		{
			if (filter.passes(arc))
				arcs.add(arc);
		}
		return Collections.unmodifiableSet(arcs);
	}

	public void remove()
	{
		validate();

		Set<? extends ManagedArc> arcs = mArcs;
		for (ManagedArc arc : arcs)
		{
			doRemoveManagedArc(arc);
			arc.removeNotifyOnly();
		}

		Set<? extends ManagedNode> nodes = mNodes;
		for (ManagedNode node : nodes)
		{
			doRemoveManagedNode(node);
			node.removeNotifyOnly();
		}

		mModel.managedGraphRemoved(this);

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

	public void setId(String id)
	{
		mId = id;
	}

	public void setManagedModel(ManagedModel model)
	{
		if (model == null && mModel != null || model != null && model != mModel)
		{
			ManagedModel oldModel = mModel;
			mModel = model;
			fireModelSet(oldModel, model);
		}
	}

	public void addManagedNode(ManagedNode node)
	{
		node.setManagedGraph(this);
		mNodes.add(node);
		fireNodeAdded(node);

		mModel.managedNodeAdded(node);
	}

	public void removeManagedNode(ManagedNode node)
	{
		doRemoveManagedNode(node);
		if (mNodes.size() == 0)
			remove();
	}

	public void addManagedArc(ManagedArc arc)
	{
		arc.setManagedGraph(this);
		mArcs.add(arc);
		fireArcAdded(arc);

		mModel.managedArcAdded(arc);
	}

	public void removeManagedArc(ManagedArc arc)
	{
		doRemoveManagedArc(arc);
	}

	public ManagedModel getManagedModel()
	{
		return mModel;
	}

	public Set<? extends ManagedNode> getManagedNodes()
	{
		return Collections.unmodifiableSet(new HashSet<ManagedNode>(mNodes));
	}

	public Set<? extends ManagedArc> getManagedArcs()
	{
		return Collections.unmodifiableSet(new HashSet<ManagedArc>(mArcs));
	}

	@Override
	public String toString()
	{
		return "BasicGraph[" + mId + "]";
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

	private void fireModelSet(ManagedModel oldModel, ManagedModel newModel)
	{
		sLog.debug("Firing " + this + ".ModelSet(oldModel:" + oldModel + ",newModel:" + newModel + ")");
		Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
		for (GraphListener listener : listeners)
			listener.modelSet(this, oldModel, newModel);
	}

	private void fireNodeAdded(ManagedNode node)
	{
		sLog.debug("Firing " + this + ".NodeAdded(node:" + node + ")");
		Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
		for (GraphListener listener : listeners)
			listener.nodeAdded(this, node);
	}

	private void fireNodeRemoved(ManagedNode node)
	{
		sLog.debug("Firing " + this + ".NodeRemoved(node:" + node + ")");
		Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
		for (GraphListener listener : listeners)
			listener.nodeRemoved(this, node);
	}

	private void fireArcAdded(ManagedArc arc)
	{
		sLog.debug("Firing " + this + ".ArcAdded(arc:" + arc + ")");
		Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
		for (GraphListener listener : listeners)
			listener.arcAdded(this, arc);
	}

	private void fireArcRemoved(ManagedArc arc)
	{
		sLog.debug("Firing " + this + ".ArcRemoved(arc:" + arc + ")");
		Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
		for (GraphListener listener : listeners)
			listener.arcRemoved(this, arc);
	}

	private void fireRemoved()
	{
		sLog.debug("Firing " + this + ".Removed()");
		Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
		for (GraphListener listener : listeners)
			listener.removed(this);
	}

	private void doRemoveManagedNode(ManagedNode node)
	{
		node.setManagedGraph(null);
		mNodes.remove(node);
		fireNodeRemoved(node);

		mModel.managedNodeRemoved(node);
	}

	private void doRemoveManagedArc(ManagedArc arc)
	{
		arc.setManagedGraph(null);
		mArcs.remove(arc);
		fireArcRemoved(arc);

		mModel.managedArcRemoved(arc);
	}
}
