package com.rjcass.graph.managed;

import com.rjcass.graph.Arc;

public interface ManagedArc extends Arc
{
	void setId(String id);

	void setManagedGraph(ManagedGraph graph);

	void setManagedNodes(ManagedNode startNode, ManagedNode endNode);

	void setManagedNodes(ManagedNode startNode, ManagedNode endNode, boolean undirected);

	void removeNotifyOnly();

	ManagedNode getStartManagedNode();

	ManagedNode getEndManagedNode();

	ManagedNode getOtherManagedNode(ManagedNode node);
}
