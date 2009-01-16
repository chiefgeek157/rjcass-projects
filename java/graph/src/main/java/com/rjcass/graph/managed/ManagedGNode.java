package com.rjcass.graph.managed;

import java.util.Set;

import com.rjcass.graph.GNode;

public interface ManagedGNode extends GNode
{
	void setId(String id);

	void setManagedGraph(ManagedGGraph graph);

	void addManagedArc(ManagedGArc arc);

	void removeManagedArc(ManagedGArc arc);

	void removeNotifyOnly();

	ManagedGGraph getManagedGraph();

	Set<? extends ManagedGArc> getManagedArcs();

	Set<? extends ManagedGNode> getAdjacentManagedNodes();
}
