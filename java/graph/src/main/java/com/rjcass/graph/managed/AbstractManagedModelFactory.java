package com.rjcass.graph.managed;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.graph.Model;
import com.rjcass.graph.ModelFactoryListener;

public abstract class AbstractManagedModelFactory implements ManagedModelFactory
{
	private static Log sLog = LogFactory.getLog(AbstractManagedModelFactory.class);

	private Set<ModelFactoryListener> mListeners;
	private ManagedEntityFactory mEntityFactory;

	public void addModelFactoryListener(ModelFactoryListener listener)
	{
		mListeners.add(listener);
	}

	public void removeModelFactoryListener(ModelFactoryListener listener)
	{
		mListeners.remove(listener);
	}

	public void setEntityFactory(ManagedEntityFactory factory)
	{
		mEntityFactory = factory;
	}

	public ManagedEntityFactory getEntityFactory()
	{
		return mEntityFactory;
	}

	public final Model createModel(String id)
	{
		return createManagedModel(id);
	}

	public final ManagedModel createManagedModel(String id)
	{
		ManagedModel model = doCreateManagedModel();
		model.setId(id);
		model.setManagedEntityFactory(mEntityFactory);
		fireModelCreated(model);
		return model;
	}

	protected AbstractManagedModelFactory()
	{
		mListeners = new HashSet<ModelFactoryListener>();
	}

	protected abstract ManagedModel doCreateManagedModel();

	private void fireModelCreated(ManagedModel model)
	{
		sLog.debug("Firing " + this + ".ModelCreated(model:" + model + ")");
		Set<ModelFactoryListener> listeners = new HashSet<ModelFactoryListener>(mListeners);
		for (ModelFactoryListener listener : listeners)
			listener.modelCreated(model);
	}
}
