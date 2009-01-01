package com.rjcass.graph.managed;

import java.util.Set;

import com.rjcass.graph.Graph;

public interface ManagedGraph extends Graph
{
	void setId(String id);

	void setManagedModel(ManagedModel model);

	void addManagedNode(ManagedNode node);

	void removeManagedNode(ManagedNode node);

	void addManagedArc(ManagedArc arc);

	void removeManagedArc(ManagedArc arc);

	ManagedModel getManagedModel();

	Set<? extends ManagedNode> getManagedNodes();

	Set<? extends ManagedArc> getManagedArcs();
}
