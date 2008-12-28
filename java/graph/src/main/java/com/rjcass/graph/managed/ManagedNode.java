package com.rjcass.graph.managed;

import java.util.Set;

import com.rjcass.graph.Node;

public interface ManagedNode extends Node
{
	ManagedGraph getManagedGraph();
	
	void setGraph(ManagedGraph graph);

	Set<? extends ManagedNode> getAdjacentManagedNodes();

	void addArc(ManagedArc arc);

	void removeArc(ManagedArc arc);
}
