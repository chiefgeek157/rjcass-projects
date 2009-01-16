package com.rjcass.graph.managed;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.graph.GModel;
import com.rjcass.graph.GModelFactoryListener;

public abstract class AbstractManagedGModelFactory implements ManagedGModelFactory
{
	private static Log sLog = LogFactory.getLog(AbstractManagedGModelFactory.class);

	private Set<GModelFactoryListener> mListeners;
	private ManagedGEntityFactory mEntityFactory;
	private int mNextModelId;

	public void addModelFactoryListener(GModelFactoryListener listener)
	{
		mListeners.add(listener);
	}

	public void removeModelFactoryListener(GModelFactoryListener listener)
	{
		mListeners.remove(listener);
	}

	public void setEntityFactory(ManagedGEntityFactory factory)
	{
		mEntityFactory = factory;
	}

	public ManagedGEntityFactory getEntityFactory()
	{
		return mEntityFactory;
	}

	public final GModel createModel()
	{
		return createManagedModel();
	}

	public final ManagedGModel createManagedModel()
	{
		ManagedGModel model = doCreateManagedModel();
		model.setId("model" + getNextModelId());
		model.setManagedEntityFactory(mEntityFactory);
		fireModelCreated(model);
		return model;
	}

	protected AbstractManagedGModelFactory()
	{
		mNextModelId = 1;
		mListeners = new HashSet<GModelFactoryListener>();
	}

	protected int getNextModelId()
	{
		return mNextModelId++;
	}

	protected abstract ManagedGModel doCreateManagedModel();

	private void fireModelCreated(ManagedGModel model)
	{
		sLog.debug("Firing " + this + ".ModelCreated(model:" + model + ")");
		Set<GModelFactoryListener> listeners = new HashSet<GModelFactoryListener>(mListeners);
		for (GModelFactoryListener listener : listeners)
			listener.modelCreated(model);
	}
}
