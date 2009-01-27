package com.rjcass.depends.basic;

import java.util.HashSet;
import java.util.Set;

import com.rjcass.depends.EntityType;
import com.rjcass.depends.spi.SPIEntityType;

public class BasicEntityType implements SPIEntityType
{
    private EntityType mSuperType;
    private Set<String> mPropertyNames;
    private String mName;

    public BasicEntityType(String name)
    {
        mPropertyNames = new HashSet<String>();
        setTypeName(name);
    }

    public EntityType getSuperType()
    {
        return mSuperType;
    }

    public void setTypeName(String name)
    {
        if (name == null)
        {
            throw new IllegalArgumentException("Entity name cannot be null");
        }
        mName = name;
    }

    public String getTypeName()
    {
        return mName;
    }

    public void setSuperType(SPIEntityType type)
    {
        if (type == null)
        {
            throw new IllegalArgumentException("Type cannot be null");
        }
        if (type.isSubtypeOf(this))
        {
            throw new IllegalArgumentException("Cyclic type error. Type '" + type.getTypeName() +
                    "' is a already subtype of '" + mName + "'");
        }

        for (String propName : mPropertyNames)
        {
            if (type.validatePropertyName(propName))
            {
                throw new IllegalArgumentException("Adding super type would result in a conflict with property name '" +
                        propName + "'");
            }
        }
        mSuperType = type;
    }

    public boolean isSubtypeOf(EntityType type)
    {
        if (type == null)
        {
            throw new IllegalArgumentException("Type cannot be null");
        }

        boolean isSubtype = equals(type);
        if (!isSubtype && mSuperType != null)
        {
            isSubtype = mSuperType.isSubtypeOf(type);
        }
        return isSubtype;
    }

    public Set<String> getPropertyNames()
    {
        Set<String> props = new HashSet<String>(mPropertyNames);
        if (mSuperType != null)
        {
            props.addAll(mSuperType.getPropertyNames());
        }
        return props;
    }

    public boolean validatePropertyName(String name)
    {
        boolean valid = mPropertyNames.contains(name);
        if (!valid && mSuperType != null)
        {
            valid = mSuperType.validatePropertyName(name);
        }
        return valid;
    }

    public void addPropertyName(String name)
    {
        if (mSuperType != null && mSuperType.validatePropertyName(name))
        {
            throw new IllegalArgumentException("Property name '" + name + "' is already defined by a parent type");
        }
        mPropertyNames.add(name);
    }
}
