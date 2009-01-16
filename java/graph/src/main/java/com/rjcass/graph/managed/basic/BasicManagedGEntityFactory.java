package com.rjcass.graph.managed.basic;

import com.rjcass.graph.managed.AbstractManagedGEntityFactory;
import com.rjcass.graph.managed.ManagedGArc;
import com.rjcass.graph.managed.ManagedGGraph;
import com.rjcass.graph.managed.ManagedGNode;

public class BasicManagedGEntityFactory extends AbstractManagedGEntityFactory
{
	public BasicManagedGEntityFactory()
	{}

	@Override
	public String toString()
	{
		return "BasicManagedModelFactory["+hashCode()+"]";
	}

	@Override
	protected ManagedGGraph doCreateGraph()
	{
		return new BasicGGraph();
	}

	@Override
	protected ManagedGNode doCreateNode()
	{
		return new BasicGNode();
	}

	@Override
	protected ManagedGArc doCreateArc()
	{
		return new BasicGArc();
	}
}
