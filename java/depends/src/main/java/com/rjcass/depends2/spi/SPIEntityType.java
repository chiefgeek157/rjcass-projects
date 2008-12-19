package com.rjcass.depends2.spi;

import com.rjcass.depends2.EntityType;

public interface SPIEntityType extends EntityType
{
    void addPropertyName(String name);

    void setSuperType(SPIEntityType type);

    void setTypeName(String name);
}
