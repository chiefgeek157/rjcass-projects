package com.rjcass.graph.basic;

import com.rjcass.graph.ModelEntity;

public abstract class AbstractModelEntity implements ModelEntity
{
    protected AbstractModelEntity()
    {
    }

    protected void validate()
    {
        if(!isValid()) throw new IllegalStateException();
    }
}