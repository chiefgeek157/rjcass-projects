package com.rjcass.depends2;

import java.util.List;

public interface Model
{
    String getName();

    void setName(String name);

    Layer getTopLayer();

    List<? extends Layer> getLayers();

    void addLayer(Layer layer);
}
