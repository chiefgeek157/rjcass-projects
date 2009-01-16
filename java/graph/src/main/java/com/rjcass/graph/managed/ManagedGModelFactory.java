package com.rjcass.graph.managed;

import com.rjcass.graph.GModelFactory;

public interface ManagedGModelFactory extends GModelFactory
{
	void setEntityFactory(ManagedGEntityFactory factory);

	ManagedGModel createManagedModel();
	
	ManagedGEntityFactory getEntityFactory();
}
