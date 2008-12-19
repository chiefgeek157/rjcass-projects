package com.rjcass.depends2.basic;

import com.rjcass.depends2.Layer;
import com.rjcass.depends2.spi.SPILayer;
import com.rjcass.depends2.spi.SPIModel;
import java.util.ArrayList;
import java.util.List;

public class BasicModel implements SPIModel
{
    private String mName;
    private SPILayer mTopLayer;

    public BasicModel()
    {
    }

    @Override
    public String getName()
    {
        return mName;
    }

    @Override
    public void setName(String name)
    {
        mName = name;
    }

    @Override
    public Layer getTopLayer()
    {
        return mTopLayer;
    }

    @Override
    public List<Layer> getLayers()
    {
        List<Layer> layers = new ArrayList<Layer>();
        Layer layer = mTopLayer;
        while (layer != null)
        {
            layers.add(layer);
            layer = layer.getChildLayer();
        }
        return layers;
    }

    public void addLayer(Layer layer)
    {
        if (layer == null)
        {
            throw new IllegalArgumentException("Layer cannot be null");
        }

        SPILayer newLayer = (SPILayer)layer;
        SPILayer bottom = mTopLayer;
        if (bottom != null)
        {
            while (bottom.getChildLayer() != null)
            {
                bottom = (SPILayer)bottom.getChildLayer();
            }
            bottom.setChildLayer(newLayer);
            newLayer.setParentLayer(bottom);
        }
        else
        {
            mTopLayer = newLayer;
        }

        newLayer.setModel(this);
    }
}
