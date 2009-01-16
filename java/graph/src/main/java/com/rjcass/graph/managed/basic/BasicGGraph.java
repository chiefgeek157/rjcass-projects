package com.rjcass.graph.managed.basic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.commons.attribute.AbstractAttributeContainer;
import com.rjcass.graph.GArc;
import com.rjcass.graph.GArcFilter;
import com.rjcass.graph.GGraphListener;
import com.rjcass.graph.GModel;
import com.rjcass.graph.GNode;
import com.rjcass.graph.GNodeFilter;
import com.rjcass.graph.managed.ManagedGArc;
import com.rjcass.graph.managed.ManagedGGraph;
import com.rjcass.graph.managed.ManagedGModel;
import com.rjcass.graph.managed.ManagedGNode;

public class BasicGGraph extends AbstractAttributeContainer implements ManagedGGraph
{
	private static Log sLog = LogFactory.getLog(BasicGGraph.class);

	private String mId;
	private String mName;
	private ManagedGModel mModel;
	private Set<ManagedGNode> mNodes;
	private Set<ManagedGArc> mArcs;
	private Set<GGraphListener> mListeners;

	public BasicGGraph()
	{
		mNodes = new HashSet<ManagedGNode>();
		mArcs = new HashSet<ManagedGArc>();
		mListeners = new HashSet<GGraphListener>();
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

	public GModel getModel()
	{
		validate();
		return getManagedModel();
	}

	public Set<? extends GNode> getNodes()
	{
		validate();
		return getManagedNodes();
	}

	public Set<? extends GNode> getNodes(GNodeFilter filter)
	{
		validate();
		Set<ManagedGNode> nodes = new HashSet<ManagedGNode>();
		for (ManagedGNode node : mNodes)
		{
			if (filter.passes(node))
				nodes.add(node);
		}
		return Collections.unmodifiableSet(nodes);
	}

	public Set<? extends GArc> getArcs()
	{
		validate();
		return getManagedArcs();
	}

	public Set<? extends GArc> getArcs(GArcFilter filter)
	{
		validate();
		Set<ManagedGArc> arcs = new HashSet<ManagedGArc>();
		for (ManagedGArc arc : mArcs)
		{
			if (filter.passes(arc))
				arcs.add(arc);
		}
		return Collections.unmodifiableSet(arcs);
	}

	public void remove()
	{
		validate();

		Set<? extends ManagedGArc> arcs = mArcs;
		for (ManagedGArc arc : arcs)
		{
			doRemoveManagedArc(arc);
			arc.removeNotifyOnly();
		}

		Set<? extends ManagedGNode> nodes = mNodes;
		for (ManagedGNode node : nodes)
		{
			doRemoveManagedNode(node);
			node.removeNotifyOnly();
		}

		mModel.managedGraphRemoved(this);

		fireRemoved();
	}

	public void addListener(GGraphListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(GGraphListener listener)
	{
		mListeners.remove(listener);
	}

	public void setId(String id)
	{
		mId = id;
	}

	public void setManagedModel(ManagedGModel model)
	{
		if (model == null && mModel != null || model != null && model != mModel)
		{
			ManagedGModel oldModel = mModel;
			mModel = model;
			fireModelSet(oldModel, model);
		}
	}

	public void addManagedNode(ManagedGNode node)
	{
		node.setManagedGraph(this);
		mNodes.add(node);
		fireNodeAdded(node);

		mModel.managedNodeAdded(node);
	}

	public void removeManagedNode(ManagedGNode node)
	{
		doRemoveManagedNode(node);
		if (mNodes.size() == 0)
			remove();
	}

	public void addManagedArc(ManagedGArc arc)
	{
		arc.setManagedGraph(this);
		mArcs.add(arc);
		fireArcAdded(arc);

		mModel.managedArcAdded(arc);
	}

	public void removeManagedArc(ManagedGArc arc)
	{
		doRemoveManagedArc(arc);
	}

	public ManagedGModel getManagedModel()
	{
		return mModel;
	}

	public Set<? extends ManagedGNode> getManagedNodes()
	{
		return Collections.unmodifiableSet(new HashSet<ManagedGNode>(mNodes));
	}

	public Set<? extends ManagedGArc> getManagedArcs()
	{
		return Collections.unmodifiableSet(new HashSet<ManagedGArc>(mArcs));
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

	private void fireModelSet(ManagedGModel oldModel, ManagedGModel newModel)
	{
		sLog.debug("Firing " + this + ".ModelSet(oldModel:" + oldModel + ",newModel:" + newModel + ")");
		Set<GGraphListener> listeners = new HashSet<GGraphListener>(mListeners);
		for (GGraphListener listener : listeners)
			listener.modelSet(this, oldModel, newModel);
	}

	private void fireNodeAdded(ManagedGNode node)
	{
		sLog.debug("Firing " + this + ".NodeAdded(node:" + node + ")");
		Set<GGraphListener> listeners = new HashSet<GGraphListener>(mListeners);
		for (GGraphListener listener : listeners)
			listener.nodeAdded(this, node);
	}

	private void fireNodeRemoved(ManagedGNode node)
	{
		sLog.debug("Firing " + this + ".NodeRemoved(node:" + node + ")");
		Set<GGraphListener> listeners = new HashSet<GGraphListener>(mListeners);
		for (GGraphListener listener : listeners)
			listener.nodeRemoved(this, node);
	}

	private void fireArcAdded(ManagedGArc arc)
	{
		sLog.debug("Firing " + this + ".ArcAdded(arc:" + arc + ")");
		Set<GGraphListener> listeners = new HashSet<GGraphListener>(mListeners);
		for (GGraphListener listener : listeners)
			listener.arcAdded(this, arc);
	}

	private void fireArcRemoved(ManagedGArc arc)
	{
		sLog.debug("Firing " + this + ".ArcRemoved(arc:" + arc + ")");
		Set<GGraphListener> listeners = new HashSet<GGraphListener>(mListeners);
		for (GGraphListener listener : listeners)
			listener.arcRemoved(this, arc);
	}

	private void fireRemoved()
	{
		sLog.debug("Firing " + this + ".Removed()");
		Set<GGraphListener> listeners = new HashSet<GGraphListener>(mListeners);
		for (GGraphListener listener : listeners)
			listener.removed(this);
	}

	private void doRemoveManagedNode(ManagedGNode node)
	{
		node.setManagedGraph(null);
		mNodes.remove(node);
		fireNodeRemoved(node);

		mModel.managedNodeRemoved(node);
	}

	private void doRemoveManagedArc(ManagedGArc arc)
	{
		arc.setManagedGraph(null);
		mArcs.remove(arc);
		fireArcRemoved(arc);

		mModel.managedArcRemoved(arc);
	}
}
