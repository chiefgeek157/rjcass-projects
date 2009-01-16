package com.rjcass.graph.managed;

import com.rjcass.graph.GArc;

public interface ManagedGArc extends GArc
{
	void setId(String id);

	void setManagedGraph(ManagedGGraph graph);

	void setManagedNodes(ManagedGNode startNode, ManagedGNode endNode);

	void setManagedNodes(ManagedGNode startNode, ManagedGNode endNode, boolean undirected);

	void removeNotifyOnly();

	ManagedGNode getStartManagedNode();

	ManagedGNode getEndManagedNode();

	ManagedGNode getOtherManagedNode(ManagedGNode node);
}
