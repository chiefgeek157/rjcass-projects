package com.rjcass.graph.managed;

import java.util.Set;

import com.rjcass.graph.Graph;

public interface ManagedGraph extends Graph
{
	ManagedModel getManagedModel();
	
	void setModel(ManagedModel model);

	Set<? extends ManagedNode> getManagedNodes();
	
	void addNode(ManagedNode node);

	void removeNode(ManagedNode node);

	void addArc(ManagedArc arc);

	void removeArc(ManagedArc arc);
}
