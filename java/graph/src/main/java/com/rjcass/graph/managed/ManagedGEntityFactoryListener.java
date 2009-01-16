package com.rjcass.graph.managed;

public interface ManagedGEntityFactoryListener
{
	void graphCreated(ManagedGGraph graph);

	void nodeCreated(ManagedGNode node);

	void arcCreated(ManagedGArc arc);
}
