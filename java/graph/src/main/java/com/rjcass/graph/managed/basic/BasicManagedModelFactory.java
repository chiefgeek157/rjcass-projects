package com.rjcass.graph.managed.basic;

import com.rjcass.graph.managed.AbstractManagedModelFactory;
import com.rjcass.graph.managed.ManagedModel;

public class BasicManagedModelFactory extends AbstractManagedModelFactory
{
	public BasicManagedModelFactory()
	{
		setEntityFactory(new BasicManagedEntityFactory());
	}

	@Override
	public String toString()
	{
		return "BasicModelFactory[" + hashCode() + "]";
	}

	@Override
	protected ManagedModel doCreateManagedModel()
	{
		return new BasicModel();
	}
}
