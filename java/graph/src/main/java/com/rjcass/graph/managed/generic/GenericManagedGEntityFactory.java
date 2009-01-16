package com.rjcass.graph.managed.generic;

import com.rjcass.graph.GGraphException;
import com.rjcass.graph.managed.AbstractManagedGEntityFactory;
import com.rjcass.graph.managed.ManagedGArc;
import com.rjcass.graph.managed.ManagedGGraph;
import com.rjcass.graph.managed.ManagedGNode;

public class GenericManagedGEntityFactory extends AbstractManagedGEntityFactory
{
	private String mGraphClassName;
	private String mNodeClassName;
	private String mArcClassName;

	public GenericManagedGEntityFactory()
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
	protected ManagedGArc doCreateArc()
	{
		ManagedGArc arc = null;
		try
		{
			Class<?> arcClass = Class.forName(mArcClassName);
			arc = (ManagedGArc)arcClass.cast(arcClass.newInstance());
		}
		catch (ClassNotFoundException e)
		{
			throw new GGraphException(e);
		}
		catch (InstantiationException e)
		{
			throw new GGraphException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new GGraphException(e);
		}
		return arc;
	}

	@Override
	protected ManagedGGraph doCreateGraph()
	{
		ManagedGGraph graph = null;
		try
		{
			Class<?> graphClass = Class.forName(mGraphClassName);
			graph = (ManagedGGraph)graphClass.cast(graphClass.newInstance());
		}
		catch (ClassNotFoundException e)
		{
			throw new GGraphException(e);
		}
		catch (InstantiationException e)
		{
			throw new GGraphException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new GGraphException(e);
		}
		return graph;
	}

	@Override
	protected ManagedGNode doCreateNode()
	{
		ManagedGNode node = null;
		try
		{
			Class<?> nodeClass = Class.forName(mNodeClassName);
			node = (ManagedGNode)nodeClass.cast(nodeClass.newInstance());
		}
		catch (ClassNotFoundException e)
		{
			throw new GGraphException(e);
		}
		catch (InstantiationException e)
		{
			throw new GGraphException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new GGraphException(e);
		}
		return node;
	}
}
