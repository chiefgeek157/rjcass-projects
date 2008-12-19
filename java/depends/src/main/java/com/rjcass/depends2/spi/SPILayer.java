package com.rjcass.depends2.spi;

import com.rjcass.depends2.Layer;

public interface SPILayer extends Layer, SPIModelElement
{
    void setParentLayer(SPILayer layer);

    void setChildLayer(SPILayer layer);

    void addEntity(SPIEntity entity);

    void removeEntity(SPIEntity entity);
}
