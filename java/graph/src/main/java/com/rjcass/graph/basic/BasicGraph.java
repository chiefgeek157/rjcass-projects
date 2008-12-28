package com.rjcass.graph.basic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.rjcass.graph.AbstractModelEntity;
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

public class BasicGraph extends AbstractModelEntity implements ManagedGraph
{
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
		return (mModel != null && mModel.isValid());
	}

	public Model getModel()
	{
		return getManagedModel();
	}

	public Set<? extends Node> getNodes()
	{
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
		// TODO Auto-generated method stub
		return null;
	}

	public Set<? extends Arc> getArcs(ArcFilter filter)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void remove()
	{
		validate();
		mModel.removeGraph(this);
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

	public ManagedModel getManagedModel()
	{
		validate();
		return mModel;
	}

	public void setModel(ManagedModel model)
	{
		if (model == null && mModel != null || model != null && model != mModel)
		{
			ManagedModel oldModel = mModel;
			mModel = model;
			fireModelSet(oldModel, model);
		}
	}

	@Override
	public Set<? extends ManagedNode> getManagedNodes()
	{
		validate();
		return Collections.unmodifiableSet(mNodes);
	}

	public void addNode(ManagedNode node)
	{
		mNodes.add(node);
		fireNodeAdded(node);

		node.setGraph(this);
	}

	public void removeNode(ManagedNode node)
	{
		node.setGraph(null);

		mNodes.remove(node);
		fireNodeRemoved(node);

		if (mNodes.size() == 0)
			mModel.removeGraph(this);
	}

	public void addArc(ManagedArc arc)
	{
		mArcs.add(arc);
	}

	public void removeArc(ManagedArc arc)
	{
		if (!mArcs.remove(arc))
			throw new IllegalStateException("Attempt to remove Arc that is not part of this Graph");
	}

	private void fireModelSet(ManagedModel oldModel, ManagedModel newModel)
	{
		Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
		for (GraphListener listener : listeners)
			listener.modelSet(this, oldModel, newModel);
	}

	private void fireNodeAdded(ManagedNode node)
	{
		Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
		for (GraphListener listener : listeners)
			listener.nodeAdded(this, node);
	}

	private void fireNodeRemoved(ManagedNode node)
	{
		Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
		for (GraphListener listener : listeners)
			listener.nodeRemoved(this, node);
	}

	private void fireRemoved()
	{
		Set<GraphListener> listeners = new HashSet<GraphListener>(mListeners);
		for (GraphListener listener : listeners)
			listener.removed(this);
	}
}
