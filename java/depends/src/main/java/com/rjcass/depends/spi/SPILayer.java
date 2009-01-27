package com.rjcass.depends.spi;

import com.rjcass.depends.Layer;

public interface SPILayer extends Layer, SPIModelElement
{
    void setParentLayer(SPILayer layer);

    void setChildLayer(SPILayer layer);

    void addEntity(SPIEntity entity);

    void removeEntity(SPIEntity entity);
}
