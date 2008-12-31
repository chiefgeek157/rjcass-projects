package com.rjcass.graph.generic;

import com.rjcass.graph.GraphException;
import com.rjcass.graph.managed.AbstractManagedModelFactory;
import com.rjcass.graph.managed.ManagedModel;

public class GenericModelFactory extends AbstractManagedModelFactory
{
	private String mModelClassName;

	public void setModelClassName(String className)
	{
		mModelClassName = className;
	}

	protected ManagedModel doCreateManagedModel()
	{
		ManagedModel model = null;
		try
		{
			Class<?> modelClass = Class.forName(mModelClassName);
			model = (ManagedModel)modelClass.cast(modelClass.newInstance());
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
		return model;
	}
}
