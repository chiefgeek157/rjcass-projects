package com.rjcass.graph.managed;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractManagedEntityFactory implements ManagedEntityFactory
{
	private static Log sLog = LogFactory.getLog(AbstractManagedEntityFactory.class);

	private Set<ManagedEntityFactoryListener> mListeners;

	public void addListener(ManagedEntityFactoryListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(ManagedEntityFactoryListener listener)
	{
		mListeners.remove(listener);
	}

	public ManagedGraph createGraph(String id)
	{
		ManagedGraph graph = doCreateGraph();
		graph.setId(id);
		fireGraphCreated(graph);
		return graph;
	}

	public ManagedNode createNode(String id)
	{
		ManagedNode node = doCreateNode();
		node.setId(id);
		fireNodeCreated(node);
		return node;
	}

	public ManagedArc createArc(String id)
	{
		ManagedArc arc = doCreateArc();
		arc.setId(id);
		fireArcCreated(arc);
		return arc;
	}

	public void fireGraphCreated(ManagedGraph graph)
	{
		sLog.debug("Firing " + this + ".GraphCreated(graph:" + graph + ")");
		Set<ManagedEntityFactoryListener> listeners = new HashSet<ManagedEntityFactoryListener>(mListeners);
		for (ManagedEntityFactoryListener listener : listeners)
			listener.graphCreated(graph);
	}

	public void fireNodeCreated(ManagedNode node)
	{
		sLog.debug("Firing " + this + ".NodeCreated(node:" + node + ")");
		Set<ManagedEntityFactoryListener> listeners = new HashSet<ManagedEntityFactoryListener>(mListeners);
		for (ManagedEntityFactoryListener listener : listeners)
			listener.nodeCreated(node);
	}

	public void fireArcCreated(ManagedArc arc)
	{
		sLog.debug("Firing " + this + ".ArcCreated(arc:" + arc + ")");
		Set<ManagedEntityFactoryListener> listeners = new HashSet<ManagedEntityFactoryListener>(mListeners);
		for (ManagedEntityFactoryListener listener : listeners)
			listener.arcCreated(arc);
	}

	protected AbstractManagedEntityFactory()
	{
		mListeners = new HashSet<ManagedEntityFactoryListener>();
	}

	protected abstract ManagedGraph doCreateGraph();

	protected abstract ManagedNode doCreateNode();

	protected abstract ManagedArc doCreateArc();
}
