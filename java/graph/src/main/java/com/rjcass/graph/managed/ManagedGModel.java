package com.rjcass.graph.managed;

import com.rjcass.graph.GModel;

public interface ManagedGModel extends GModel
{
	void setId(String id);

	void setManagedEntityFactory(ManagedGEntityFactory factory);

	ManagedGArc addManagedArc(ManagedGNode node1, ManagedGNode node2, boolean directed);

	void removeManagedArc(ManagedGArc arc);

	void managedNodeAdded(ManagedGNode node);

	void managedNodeRemoved(ManagedGNode node);

	void managedArcAdded(ManagedGArc arc);

	void managedArcRemoved(ManagedGArc arc);

	void managedGraphRemoved(ManagedGGraph graph);
}
