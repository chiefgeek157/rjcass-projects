package com.rjcass.graph.managed;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractManagedEntityFactory implements ManagedEntityFactory
{
	private Set<ManagedEntityFactoryListener> mListeners;

	public void addListener(ManagedEntityFactoryListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(ManagedEntityFactoryListener listener)
	{
		mListeners.remove(listener);
	}

	public ManagedGraph createGraph()
	{
		ManagedGraph graph = doCreateGraph();
		fireGraphCreated(graph);
		return graph;
	}

	public ManagedNode createNode()
	{
		ManagedNode node = doCreateNode();
		fireNodeCreated(node);
		return node;
	}

	public ManagedArc createArc()
	{
		ManagedArc arc = doCreateArc();
		fireArcCreated(arc);
		return arc;
	}

	public void fireGraphCreated(ManagedGraph graph)
	{
		Set<ManagedEntityFactoryListener> listeners = new HashSet<ManagedEntityFactoryListener>(mListeners);
		for (ManagedEntityFactoryListener listener : listeners)
			listener.graphCreated(graph);
	}

	public void fireNodeCreated(ManagedNode node)
	{
		Set<ManagedEntityFactoryListener> listeners = new HashSet<ManagedEntityFactoryListener>(mListeners);
		for (ManagedEntityFactoryListener listener : listeners)
			listener.nodeCreated(node);
	}

	public void fireArcCreated(ManagedArc arc)
	{
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
