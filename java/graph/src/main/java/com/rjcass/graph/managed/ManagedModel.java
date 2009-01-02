package com.rjcass.graph.managed;

import com.rjcass.graph.Model;

public interface ManagedModel extends Model
{
	void setId(String id);

	void setManagedEntityFactory(ManagedEntityFactory factory);

	ManagedArc addManagedArc(ManagedNode node1, ManagedNode node2, boolean directed);

	void removeManagedArc(ManagedArc arc);

	void managedNodeAdded(ManagedNode node);

	void managedNodeRemoved(ManagedNode node);

	void managedArcAdded(ManagedArc arc);

	void managedArcRemoved(ManagedArc arc);

	void managedGraphRemoved(ManagedGraph graph);
}
