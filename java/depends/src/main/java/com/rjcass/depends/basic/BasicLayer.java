package com.rjcass.depends.basic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.rjcass.depends.Entity;
import com.rjcass.depends.Layer;
import com.rjcass.depends.Model;
import com.rjcass.depends.spi.SPIEntity;
import com.rjcass.depends.spi.SPILayer;
import com.rjcass.depends.spi.SPIModel;

public class BasicLayer extends GModel implements SPILayer
{
	private String mName;
	private SPILayer mParent;
	private SPILayer mChild;
	private SPIModel mModel;
	private Set<SPIEntity> mEntities;

	public BasicLayer()
	{
		mEntities = new HashSet<SPIEntity>();
	}

	public void setParentLayer(SPILayer layer)
	{
		mParent = layer;
	}

	public void setChildLayer(SPILayer layer)
	{
		mChild = layer;
	}

	public String getName()
	{
		return mName;
	}

	public void setName(String name)
	{
		mName = name;
	}

	public Layer getParentLayer()
	{
		return mParent;
	}

	public Layer getChildLayer()
	{
		return mChild;
	}

	public boolean isTopLayer()
	{
		return (mParent == null);
	}

	public Set<? extends Entity> getEntities()
	{
		return Collections.unmodifiableSet(mEntities);
	}

	public Model getModel()
	{
		return mModel;
	}

	public void setModel(SPIModel model)
	{
		if (model == null)
		{
			throw new IllegalArgumentException("Model cannot be null");

		}
		mModel = model;
	}

	public void addEntity(SPIEntity entity)
	{
		if (entity == null)
		{
			throw new IllegalArgumentException("Entity cannot be null");

		}
		mEntities.add(entity);
	}

	public void removeEntity(SPIEntity entity)
	{
		if (entity == null)
		{
			throw new IllegalArgumentException("Entity cannot be null");

		}
		mEntities.remove(entity);
	}
}
