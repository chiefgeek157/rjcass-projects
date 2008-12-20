package com.rjcass.depends2.basic;

import com.rjcass.depends2.DependsException;
import com.rjcass.depends2.Entity;
import com.rjcass.depends2.EntityFactory;
import com.rjcass.depends2.EntityType;
import com.rjcass.depends2.spi.SPIEntity;
import com.rjcass.depends2.spi.SPIEntityType;

public class GenericEntityFactory implements EntityFactory
{
    private String mEntityClassName;

    public GenericEntityFactory()
    {
    }

    public GenericEntityFactory(String entityClassName)
    {
        setEntityClassName(entityClassName);
    }

    public Entity createEntity(EntityType type, String name)
    {
        SPIEntity entity = null;
        try
        {
            Class<?> entityClass = Class.forName(mEntityClassName);
            entity = (SPIEntity)entityClass.newInstance();
            entity.setName(name);
            entity.setType((SPIEntityType)type);
        }
        catch (InstantiationException e)
        {
            throw new DependsException("Could not instatiate class", e);
        }
        catch (IllegalAccessException e)
        {
            throw new DependsException("Could not instatiate class", e);
        }
        catch (ClassNotFoundException e)
        {
            throw new DependsException("Could not instatiate class", e);
        }
        return entity;
    }

    public void setEntityClassName(String entityClassName)
    {
        mEntityClassName = entityClassName;
    }
}
