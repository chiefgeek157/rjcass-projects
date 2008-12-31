package com.rjcass.graph.basic;

import com.rjcass.graph.managed.AbstractManagedModelFactory;
import com.rjcass.graph.managed.ManagedModel;

public class BasicModelFactory extends AbstractManagedModelFactory
{
	public BasicModelFactory()
	{
		setEntityFactory(new BasicManagedEntityFactory());
	}

	protected ManagedModel doCreateManagedModel()
	{
		return new BasicModel();
	}
}
