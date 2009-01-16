package com.rjcass.graph.managed.basic;

import com.rjcass.graph.managed.AbstractManagedGModelFactory;
import com.rjcass.graph.managed.ManagedGModel;

public class BasicManagedGModelFactory extends AbstractManagedGModelFactory
{
	public BasicManagedGModelFactory()
	{
		setEntityFactory(new BasicManagedGEntityFactory());
	}

	@Override
	public String toString()
	{
		return "BasicModelFactory[" + hashCode() + "]";
	}

	@Override
	protected ManagedGModel doCreateManagedModel()
	{
		return new BasicGModel();
	}
}
