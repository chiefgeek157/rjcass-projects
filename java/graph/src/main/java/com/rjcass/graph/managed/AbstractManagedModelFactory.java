package com.rjcass.graph.managed;

import java.util.HashSet;
import java.util.Set;

import com.rjcass.graph.Model;
import com.rjcass.graph.ModelFactoryListener;

public abstract class AbstractManagedModelFactory implements ManagedModelFactory
{
	private Set<ModelFactoryListener> mListeners;
	private ModelEntityFactory mEntityFactory;

	@Override
	public void addModelFactoryListener(ModelFactoryListener listener)
	{
		mListeners.add(listener);
	}

	@Override
	public void removeModelFactoryListener(ModelFactoryListener listener)
	{
		mListeners.remove(listener);
	}

	public void SetEntityFactory(ModelEntityFactory factory)
	{
		mEntityFactory = factory;
	}

	@Override
	public final Model createModel()
	{
		ManagedModel model = doCreateModel();
		model.setEntityFactory(mEntityFactory);
		fireModelCreated(model);
		return model;
	}

	protected AbstractManagedModelFactory()
	{
		mListeners = new HashSet<ModelFactoryListener>();
	}

	protected ModelEntityFactory getEntityFactory()
	{
		return mEntityFactory;
	}

	protected abstract ManagedModel doCreateModel();

	private void fireModelCreated(ManagedModel model)
	{
		Set<ModelFactoryListener> listeners = new HashSet<ModelFactoryListener>(mListeners);
		for (ModelFactoryListener listener : listeners)
			listener.modelCreated(model);
	}
}
