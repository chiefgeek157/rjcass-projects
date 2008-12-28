package com.rjcass.graph.managed;

import com.rjcass.graph.Arc;

public interface ManagedArc extends Arc
{
	ManagedNode getStartManagedNode();

	ManagedNode getEndManagedNode();

	ManagedNode getOtherManagedNode(ManagedNode node);

	void setStartNode(ManagedNode node);

	void setEndNode(ManagedNode node);
}
