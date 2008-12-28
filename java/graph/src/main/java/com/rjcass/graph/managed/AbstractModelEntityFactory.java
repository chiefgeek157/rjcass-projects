package com.rjcass.graph.managed;

import java.util.HashSet;
import java.util.Set;



public abstract class AbstractModelEntityFactory implements ModelEntityFactory
{
	private Set<ModelEntityFactoryListener> mListeners;

	public void addListener(ModelEntityFactoryListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(ModelEntityFactoryListener listener)
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
		Set<ModelEntityFactoryListener> listeners = new HashSet<ModelEntityFactoryListener>(mListeners);
		for (ModelEntityFactoryListener listener : listeners)
			listener.graphCreated(graph);
	}

	public void fireNodeCreated(ManagedNode node)
	{
		Set<ModelEntityFactoryListener> listeners = new HashSet<ModelEntityFactoryListener>(mListeners);
		for (ModelEntityFactoryListener listener : listeners)
			listener.nodeCreated(node);
	}

	public void fireArcCreated(ManagedArc arc)
	{
		Set<ModelEntityFactoryListener> listeners = new HashSet<ModelEntityFactoryListener>(mListeners);
		for (ModelEntityFactoryListener listener : listeners)
			listener.arcCreated(arc);
	}

	protected AbstractModelEntityFactory()
	{
		mListeners = new HashSet<ModelEntityFactoryListener>();
	}

	protected abstract ManagedGraph doCreateGraph();

	protected abstract ManagedNode doCreateNode();

	protected abstract ManagedArc doCreateArc();
}
