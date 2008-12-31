package com.rjcass.graph.managed;

import com.rjcass.graph.Arc;

public interface ManagedArc extends Arc
{
	void setManagedGraph(ManagedGraph graph);
	
	void removeNotifyOnly();

	ManagedNode getStartManagedNode();

	ManagedNode getEndManagedNode();

	ManagedNode getOtherManagedNode(ManagedNode node);
}
