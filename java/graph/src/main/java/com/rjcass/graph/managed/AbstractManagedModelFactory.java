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
	private int mNextModelId;

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

	public final Model createModel()
	{
		return createManagedModel();
	}

	public final ManagedModel createManagedModel()
	{
		ManagedModel model = doCreateManagedModel();
		model.setId("model" + getNextModelId());
		model.setManagedEntityFactory(mEntityFactory);
		fireModelCreated(model);
		return model;
	}

	protected AbstractManagedModelFactory()
	{
		mNextModelId = 1;
		mListeners = new HashSet<ModelFactoryListener>();
	}

	protected int getNextModelId()
	{
		return mNextModelId++;
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
