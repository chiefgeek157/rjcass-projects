package com.rjcass.graph.managed;

public interface ManagedEntityFactory
{
	public ManagedGraph createGraph(String id);

	public ManagedNode createNode(String id);

	public ManagedArc createArc(String id);

	public void addListener(ManagedEntityFactoryListener listener);

	public void removeListener(ManagedEntityFactoryListener listener);
}
