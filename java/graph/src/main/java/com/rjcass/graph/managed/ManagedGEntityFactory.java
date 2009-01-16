package com.rjcass.graph.managed;

public interface ManagedGEntityFactory
{
	public ManagedGGraph createGraph();

	public ManagedGNode createNode();

	public ManagedGArc createArc();

	public void addListener(ManagedGEntityFactoryListener listener);

	public void removeListener(ManagedGEntityFactoryListener listener);
}
