package com.rjcass.graph.managed;

import java.util.Set;

import com.rjcass.graph.Node;

public interface ManagedNode extends Node
{
	void setId(String id);

	void setManagedGraph(ManagedGraph graph);

	void addManagedArc(ManagedArc arc);

	void removeManagedArc(ManagedArc arc);

	void removeNotifyOnly();

	ManagedGraph getManagedGraph();

	Set<? extends ManagedArc> getManagedArcs();

	Set<? extends ManagedNode> getAdjacentManagedNodes();
}
