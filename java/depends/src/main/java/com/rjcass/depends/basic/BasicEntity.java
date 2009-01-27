package com.rjcass.depends.basic;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.rjcass.depends.Dependency;
import com.rjcass.depends.DependsException;
import com.rjcass.depends.Entity;
import com.rjcass.depends.EntityType;
import com.rjcass.depends.spi.SPIDependency;
import com.rjcass.depends.spi.SPIEntity;
import com.rjcass.depends.spi.SPIEntityType;

public class BasicEntity implements SPIEntity
{
	public String mName;
	private EntityType mType;
	private Set<SPIDependency> mDependencies;
	private Set<SPIDependency> mDependents;
	private SPIEntity mAbstraction;
	private Set<SPIEntity> mDetails;
	private Map<String, String> mProperties;

	public BasicEntity()
	{
		mDependencies = new HashSet<SPIDependency>();
		mDependents = new HashSet<SPIDependency>();
		mDetails = new HashSet<SPIEntity>();
		mProperties = new HashMap<String, String>();
	}
	
	public BasicEntity(EntityType type, String name)
	{
		this();
		
		if (type == null)
		{
			throw new IllegalArgumentException("EntityType cannot be null");
		}

		mType = type;
		setName(name);
	}

	public void addDependency(SPIDependency dependency)
	{
		if (dependency.getSource().equals(this))
		{
			mDependencies.add(dependency);
		}
		else if (dependency.getTarget().equals(this))
		{
			mDependents.add(dependency);
		}
		else
		{
			throw new DependsException("Attempt to add a Dependency that does not point to this Entity");
		}
	}

	public Set<? extends Dependency> getDependencies()
	{
		return Collections.unmodifiableSet(mDependencies);
	}

	public Set<? extends Dependency> getDependents()
	{
		return Collections.unmodifiableSet(mDependents);
	}

	public Set<? extends Entity> getDependencyEntities()
	{
		Set<Entity> entities = new HashSet<Entity>();

		for (Dependency dep : mDependencies)
		{
			entities.add(dep.getTarget());
		}

		return entities;
	}

	public Set<? extends Entity> getDependentEntities()
	{
		Set<Entity> entities = new HashSet<Entity>();

		for (Dependency dep : mDependents)
		{
			entities.add(dep.getTarget());
		}

		return entities;
	}

	public void removeDependency(SPIDependency dependency)
	{
		if (!mDependencies.remove(dependency))
		{
			if (!mDependents.remove(dependency))
			{
				throw new DependsException("Attempt to remove a Dependency that does not point to this Entity");
			}
		}
	}

	public boolean dependsOn(Entity entity)
	{
		return getDependencyEntities().contains(entity);
	}

	public boolean dependsOn(Entity entity, boolean transiency)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void setAbstraction(Entity entity)
	{
		if (entity == null)
		{
			throw new IllegalArgumentException("Abstraction cannot be null");
		}
		if (entity.isDetailOf(this))
		{
			throw new IllegalArgumentException("Setting abstraction to '" + entity.getName() + "' would result in a cycle");
		}
		mAbstraction = (SPIEntity)entity;
		mAbstraction.addDetail(this);
	}

	public Entity getAbstraction()
	{
		return mAbstraction;
	}

	public void removeAbstraction()
	{
		mAbstraction = null;
	}

	public void addDetail(SPIEntity entity)
	{
		mDetails.add(entity);
	}

	public Set<? extends Entity> getDetails()
	{
		return Collections.unmodifiableSet(mDetails);
	}

	public boolean isDetailOf(Entity entity)
	{
		boolean isDetail = false;
		if (mAbstraction != null)
		{
			if (mAbstraction.equals(entity))
			{
				isDetail = true;
			}
			else
			{
				isDetail = mAbstraction.isDetailOf(entity);
			}
		}
		return isDetail;
	}

	public void removeDetail(SPIEntity entity)
	{
		if (!mDetails.remove(entity))
		{
			throw new DependsException("Attempt to remove Detail that is not part of this Entity");
		}
	}

	public int getLevelOfAbstraction()
	{
		int loa = 0;
		if (mAbstraction != null)
		{
			loa = mAbstraction.getLevelOfAbstraction() + 1;
		}
		return loa;
	}

	public void setName(String name)
	{
		mName = name;
	}

	public String getName()
	{
		return mName;
	}

	public Map<String, String> getProperties()
	{
		return Collections.unmodifiableMap(mProperties);
	}

	public String getProperty(String name)
	{
		return mProperties.get(name);
	}

	public void setType(SPIEntityType type)
	{
		mType = type;
	}
	
	public EntityType getType()
	{
		return mType;
	}

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
}
