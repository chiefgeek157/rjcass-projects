package com.rjcass.graph.basic;

import com.rjcass.graph.managed.AbstractManagedEntityFactory;
import com.rjcass.graph.managed.ManagedArc;
import com.rjcass.graph.managed.ManagedGraph;
import com.rjcass.graph.managed.ManagedNode;

public class BasicManagedEntityFactory extends AbstractManagedEntityFactory
{
	public BasicManagedEntityFactory()
	{}

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
