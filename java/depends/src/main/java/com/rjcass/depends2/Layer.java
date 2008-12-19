package com.rjcass.depends2;

import java.util.Set;

public interface Layer extends ModelElement
{
    String getName();

    void setName(String name);

    Layer getParentLayer();

    Layer getChildLayer();

    boolean isTopLayer();

    Set<? extends Entity> getEntities();
}
