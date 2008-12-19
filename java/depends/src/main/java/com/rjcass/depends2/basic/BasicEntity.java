package com.rjcass.depends2.basic;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.rjcass.depends2.Dependency;
import com.rjcass.depends2.DependsException;
import com.rjcass.depends2.Entity;
import com.rjcass.depends2.EntityType;
import com.rjcass.depends2.spi.SPIDependency;
import com.rjcass.depends2.spi.SPIEntity;

public class BasicEntity implements SPIEntity
{
    private String mName;
    private EntityType mType;
    private Set<SPIDependency> mDependencies;
    private Set<SPIDependency> mDependents;
    private SPIEntity mAbstraction;
    private Set<SPIEntity> mDetails;
    private Map<String, String> mProperties;

    public BasicEntity(EntityType type, String name)
    {
        if (type == null)
        {
            throw new IllegalArgumentException("EntityType cannot be null");
        }

        mType = type;
        mDependencies = new HashSet<SPIDependency>();
        mDependents = new HashSet<SPIDependency>();
        mDetails = new HashSet<SPIEntity>();
        mProperties = new HashMap<String, String>();
        setName(name);
    }

    @Override
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

    @Override
    public Set<? extends Dependency> getDependencies()
    {
        return Collections.unmodifiableSet(mDependencies);
    }

    @Override
    public Set<? extends Dependency> getDependents()
    {
        return Collections.unmodifiableSet(mDependents);
    }

    @Override
    public Set<? extends Entity> getDependencyEntities()
    {
        Set<Entity> entities = new HashSet<Entity>();

        for (Dependency dep : mDependencies)
        {
            entities.add(dep.getTarget());
        }

        return entities;
    }

    @Override
    public Set<? extends Entity> getDependentEntities()
    {
        Set<Entity> entities = new HashSet<Entity>();

        for (Dependency dep : mDependents)
        {
            entities.add(dep.getTarget());
        }

        return entities;
    }

    @Override
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

    @Override
    public boolean dependsOn(Entity entity)
    {
        return getDependencyEntities().contains(entity);
    }

    @Override
    public boolean dependsOn(Entity entity, boolean transiency)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setAbstraction(Entity entity)
    {
        if (entity == null)
        {
            throw new IllegalArgumentException("Abstraction cannot be null");
        }
        if (entity.isDetailOf(this))
        {
            throw new IllegalArgumentException("Setting abstraction to '" + entity.getName() +
                    "' would result in a cycle");
        }
        mAbstraction = (SPIEntity)entity;
        mAbstraction.addDetail(this);
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
    public void addDetail(SPIEntity entity)
    {
        mDetails.add(entity);
    }

    @Override
    public Set<? extends Entity> getDetails()
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

    @Override
    public void removeDetail(SPIEntity entity)
    {
        if (!mDetails.remove(entity))
        {
            throw new DependsException("Attempt to remove Detail that is not part of this Entity");
        }
    }

    @Override
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
            throw new IllegalArgumentException("Property name '" + name + "' not allowed for EntityType '" + mType.
                    getTypeName() + "'");
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
            throw new IllegalArgumentException("Property name '" + name + "' not allowed for EntityType '" + mType.
                    getTypeName() + "'");
        }
    }
}
