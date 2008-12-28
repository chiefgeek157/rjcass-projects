package com.rjcass.graph;


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