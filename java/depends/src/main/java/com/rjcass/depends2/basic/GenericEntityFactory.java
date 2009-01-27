package com.rjcass.depends2.basic;

import com.rjcass.depends.DependsException;
import com.rjcass.depends.Entity;
import com.rjcass.depends.EntityFactory;
import com.rjcass.depends.EntityType;
import com.rjcass.depends.spi.SPIEntity;
import com.rjcass.depends.spi.SPIEntityType;

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
