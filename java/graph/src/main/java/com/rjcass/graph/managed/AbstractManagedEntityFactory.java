package com.rjcass.graph.managed;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractManagedEntityFactory implements ManagedEntityFactory
{
	private static Log sLog = LogFactory.getLog(AbstractManagedEntityFactory.class);

	private Set<ManagedEntityFactoryListener> mListeners;
	private int mNextGraphId;
	private int mNextNodeId;
	private int mNextArcId;

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
		graph.setId("graph"+getNextGraphId());
		fireGraphCreated(graph);
		return graph;
	}

	public ManagedNode createNode()
	{
		ManagedNode node = doCreateNode();
		node.setId("node"+getNextNodeId());
		fireNodeCreated(node);
		return node;
	}

	public ManagedArc createArc()
	{
		ManagedArc arc = doCreateArc();
		arc.setId("arc"+getNextArcId());
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
		mNextGraphId = 1;
		mNextNodeId = 1;
		mNextArcId = 1;
		mListeners = new HashSet<ManagedEntityFactoryListener>();
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

	protected abstract ManagedGraph doCreateGraph();

	protected abstract ManagedNode doCreateNode();

	protected abstract ManagedArc doCreateArc();
}
