package com.rjcass.graph.managed;

public interface ManagedEntityFactory
{
	public ManagedGraph createGraph();

	public ManagedNode createNode();

	public ManagedArc createArc();

	public void addListener(ManagedEntityFactoryListener listener);

	public void removeListener(ManagedEntityFactoryListener listener);
}
