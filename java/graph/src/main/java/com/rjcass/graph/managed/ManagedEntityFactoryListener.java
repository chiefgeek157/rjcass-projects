package com.rjcass.graph.managed;

public interface ManagedEntityFactoryListener
{
	void graphCreated(ManagedGraph graph);

	void nodeCreated(ManagedNode node);

	void arcCreated(ManagedArc arc);
}
