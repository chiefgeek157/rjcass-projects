package com.rjcass.depends.spi;

import com.rjcass.depends.EntityType;

public interface SPIEntityType extends EntityType
{
    void addPropertyName(String name);

    void setSuperType(SPIEntityType type);

    void setTypeName(String name);
}
