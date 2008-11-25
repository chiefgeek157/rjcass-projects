package com.rjcass.depends2.basic;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.rjcass.depends2.Entity;
import com.rjcass.depends2.EntityType;

public class BasicEntity implements Entity
{
	private String mName;
	private Map<String, String> mProperties;
	private EntityType mType;
	private Entity mAbstraction;
	private Set<Entity> mDetails;

	public BasicEntity(EntityType type, String name)
	{
		if (type == null)
			throw new IllegalArgumentException("EntityType cannot be null");

		mProperties = new HashMap<String, String>();
		mDetails = new HashSet<Entity>();
		mType = type;
		setName(name);
	}

	public void setAbstraction(Entity entity)
	{
		if (entity == null)
			throw new IllegalArgumentException("Abstraction cannot be null");
		if (entity.isDetailOf(this))
			throw new IllegalArgumentException("Setting abstraction to '" + entity.getName() + "' would result in a cycle");
		mAbstraction = entity;
		mAbstraction.add
	}

	@Override
	public Entity getAbstraction()
	{
		return mAbstraction;
	}

	public void removeAbstraction()
	{
		mAbstraction = null;
	}

	@Override
	public Set<Entity> getDetails()
	{
		return Collections.unmodifiableSet(mDetails);
	}

	@Override
	public boolean isDetailOf(Entity entity)
	{
		boolean isDetail = false;
		if (mAbstraction != null)
		{
			if (mAbstraction.equals(entity))
				isDetail = true;
			else
				isDetail = mAbstraction.isDetailOf(entity);
		}
		return isDetail;
	}

	@Override
	public int getLevelOfAbstraction()
	{
		int loa = 0;
		if (mAbstraction != null)
			loa = mAbstraction.getLevelOfAbstraction() + 1;
		return loa;
	}

	public void setName(String name)
	{
		mName = name;
	}

	@Override
	public String getName()
	{
		return mName;
	}

	@Override
	public Map<String, String> getProperties()
	{
		return Collections.unmodifiableMap(mProperties);
	}

	@Override
	public String getProperty(String name)
	{
		return mProperties.get(name);
	}

	@Override
	public EntityType getType()
	{
		return mType;
	}

	@Override
	public void setProperty(String name, String value)
	{
		if (mType.validatePropertyName(name))
		{
			if (value != null)
			{
				mProperties.put(name, value);
			}
			else
			{
				throw new IllegalArgumentException("Value cannot be null");
			}
		}
		else
		{
			throw new IllegalArgumentException("Property name '" + name + "' not allowed for EntityType '" + mType.getTypeName()
					+ "'");
		}
	}

	@Override
	public void removeProperty(String name)
	{
		if (mType.validatePropertyName(name))
		{
			mProperties.remove(name);
		}
		else
		{
			throw new IllegalArgumentException("Property name '" + name + "' not allowed for EntityType '" + mType.getTypeName()
					+ "'");
		}
	}

	private void addDetail(Entity entity)
	{
		mDetails.add(entity);
	}
}
