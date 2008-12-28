package com.rjcass.graph.managed;

public interface ModelEntityFactory
{
	public ManagedGraph createGraph();

	public ManagedNode createNode();

	public ManagedArc createArc();

	public void addListener(ModelEntityFactoryListener listener);

	public void removeListener(ModelEntityFactoryListener listener);
}
