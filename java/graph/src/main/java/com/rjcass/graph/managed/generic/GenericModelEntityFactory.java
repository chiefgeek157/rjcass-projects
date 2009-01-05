package com.rjcass.graph.managed.generic;

import com.rjcass.graph.GraphException;
import com.rjcass.graph.managed.AbstractManagedEntityFactory;
import com.rjcass.graph.managed.ManagedArc;
import com.rjcass.graph.managed.ManagedGraph;
import com.rjcass.graph.managed.ManagedNode;

public class GenericModelEntityFactory extends AbstractManagedEntityFactory
{
	private String mGraphClassName;
	private String mNodeClassName;
	private String mArcClassName;

	public GenericModelEntityFactory()
	{}

	public void setGraphClassName(String graphClassName)
	{
		mGraphClassName = graphClassName;
	}

	public void setNodeClassName(String nodeClassName)
	{
		mNodeClassName = nodeClassName;
	}

	public void setArcClassName(String arcClassName)
	{
		mArcClassName = arcClassName;
	}

	@Override
	protected ManagedArc doCreateArc()
	{
		ManagedArc arc = null;
		try
		{
			Class<?> arcClass = Class.forName(mArcClassName);
			arc = (ManagedArc)arcClass.cast(arcClass.newInstance());
		}
		catch (ClassNotFoundException e)
		{
			throw new GraphException(e);
		}
		catch (InstantiationException e)
		{
			throw new GraphException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new GraphException(e);
		}
		return arc;
	}

	@Override
	protected ManagedGraph doCreateGraph()
	{
		ManagedGraph graph = null;
		try
		{
			Class<?> graphClass = Class.forName(mGraphClassName);
			graph = (ManagedGraph)graphClass.cast(graphClass.newInstance());
		}
		catch (ClassNotFoundException e)
		{
			throw new GraphException(e);
		}
		catch (InstantiationException e)
		{
			throw new GraphException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new GraphException(e);
		}
		return graph;
	}

	@Override
	protected ManagedNode doCreateNode()
	{
		ManagedNode node = null;
		try
		{
			Class<?> nodeClass = Class.forName(mNodeClassName);
			node = (ManagedNode)nodeClass.cast(nodeClass.newInstance());
		}
		catch (ClassNotFoundException e)
		{
			throw new GraphException(e);
		}
		catch (InstantiationException e)
		{
			throw new GraphException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new GraphException(e);
		}
		return node;
	}
}
