package com.rjcass.graph.managed;

import com.rjcass.graph.Model;

public interface ManagedModel extends Model
{
	public void setEntityFactory(ManagedEntityFactory factory);

	ManagedArc addArc(ManagedNode node1, ManagedNode node2);

	void removeGraph(ManagedGraph graph);
	
	void removeNode(ManagedNode node);

	boolean removeArc(ManagedArc arc);
}
