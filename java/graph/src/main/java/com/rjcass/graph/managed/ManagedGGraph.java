package com.rjcass.graph.managed;

import java.util.Set;

import com.rjcass.graph.GGraph;

public interface ManagedGGraph extends GGraph
{
	void setId(String id);

	void setManagedModel(ManagedGModel model);

	void addManagedNode(ManagedGNode node);

	void removeManagedNode(ManagedGNode node);

	void addManagedArc(ManagedGArc arc);

	void removeManagedArc(ManagedGArc arc);

	ManagedGModel getManagedModel();

	Set<? extends ManagedGNode> getManagedNodes();

	Set<? extends ManagedGArc> getManagedArcs();
}
