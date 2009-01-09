package com.rjcass.graph.managed.basic;

import com.rjcass.graph.managed.AbstractManagedEntityFactory;
import com.rjcass.graph.managed.ManagedArc;
import com.rjcass.graph.managed.ManagedGraph;
import com.rjcass.graph.managed.ManagedNode;

public class BasicManagedEntityFactory extends AbstractManagedEntityFactory
{
	public BasicManagedEntityFactory()
	{}

	@Override
	public String toString()
	{
		return "BasicManagedModelFactory["+hashCode()+"]";
	}

	@Override
	protected ManagedGraph doCreateGraph()
	{
		return new BasicGraph();
	}

	@Override
	protected ManagedNode doCreateNode()
	{
		return new BasicNode();
	}

	@Override
	protected ManagedArc doCreateArc()
	{
		return new BasicArc();
	}
}