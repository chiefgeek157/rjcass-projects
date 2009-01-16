package com.rjcass.graph.managed.generic;

import com.rjcass.graph.GGraphException;
import com.rjcass.graph.managed.AbstractManagedGModelFactory;
import com.rjcass.graph.managed.ManagedGModel;

public class GenericManagedGModelFactory extends AbstractManagedGModelFactory
{
	private String mModelClassName;

	public void setModelClassName(String className)
	{
		mModelClassName = className;
	}

	protected ManagedGModel doCreateManagedModel()
	{
		ManagedGModel model = null;
		try
		{
			Class<?> modelClass = Class.forName(mModelClassName);
			model = (ManagedGModel)modelClass.cast(modelClass.newInstance());
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
		return model;
	}
}
