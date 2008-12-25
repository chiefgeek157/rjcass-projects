package com.rjcass.graph.basic.manage;

public interface ModelEntityFactoryListener
{
	void modelCreated(ManagedModel model);

	void graphCreated(ManagedGraph graph);

	void nodeCreated(ManagedNode node);

	void arcCreated(ManagedArc arc);
}
