package com.rjcass.graph.managed;

import com.rjcass.graph.ModelFactory;

public interface ManagedModelFactory extends ModelFactory
{
	void setEntityFactory(ManagedEntityFactory factory);
}
