package com.rjcass.graph.managed;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractManagedGEntityFactory implements ManagedGEntityFactory
{
	private static Log sLog = LogFactory.getLog(AbstractManagedGEntityFactory.class);

	private Set<ManagedGEntityFactoryListener> mListeners;
	private int mNextGraphId;
	private int mNextNodeId;
	private int mNextArcId;

	public void addListener(ManagedGEntityFactoryListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(ManagedGEntityFactoryListener listener)
	{
		mListeners.remove(listener);
	}

	public ManagedGGraph createGraph()
	{
		ManagedGGraph graph = doCreateGraph();
		graph.setId("graph"+getNextGraphId());
		fireGraphCreated(graph);
		return graph;
	}

	public ManagedGNode createNode()
	{
		ManagedGNode node = doCreateNode();
		node.setId("node"+getNextNodeId());
		fireNodeCreated(node);
		return node;
	}

	public ManagedGArc createArc()
	{
		ManagedGArc arc = doCreateArc();
		arc.setId("arc"+getNextArcId());
		fireArcCreated(arc);
		return arc;
	}

	public void fireGraphCreated(ManagedGGraph graph)
	{
		sLog.debug("Firing " + this + ".GraphCreated(graph:" + graph + ")");
		Set<ManagedGEntityFactoryListener> listeners = new HashSet<ManagedGEntityFactoryListener>(mListeners);
		for (ManagedGEntityFactoryListener listener : listeners)
			listener.graphCreated(graph);
	}

	public void fireNodeCreated(ManagedGNode node)
	{
		sLog.debug("Firing " + this + ".NodeCreated(node:" + node + ")");
		Set<ManagedGEntityFactoryListener> listeners = new HashSet<ManagedGEntityFactoryListener>(mListeners);
		for (ManagedGEntityFactoryListener listener : listeners)
			listener.nodeCreated(node);
	}

	public void fireArcCreated(ManagedGArc arc)
	{
		sLog.debug("Firing " + this + ".ArcCreated(arc:" + arc + ")");
		Set<ManagedGEntityFactoryListener> listeners = new HashSet<ManagedGEntityFactoryListener>(mListeners);
		for (ManagedGEntityFactoryListener listener : listeners)
			listener.arcCreated(arc);
	}

	protected AbstractManagedGEntityFactory()
	{
		mNextGraphId = 1;
		mNextNodeId = 1;
		mNextArcId = 1;
		mListeners = new HashSet<ManagedGEntityFactoryListener>();
	}

	protected int getNextGraphId()
	{
		return mNextGraphId++;
	}

	protected int getNextNodeId()
	{
		return mNextNodeId++;
	}

	protected int getNextArcId()
	{
		return mNextArcId++;
	}

	protected abstract ManagedGGraph doCreateGraph();

	protected abstract ManagedGNode doCreateNode();

	protected abstract ManagedGArc doCreateArc();
}
