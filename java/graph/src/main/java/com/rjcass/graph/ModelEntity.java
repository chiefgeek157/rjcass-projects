package com.rjcass.graph;

public abstract class ModelEntity
{
    public abstract boolean isValid();

    protected ModelEntity()
    {
    }

    protected void validate()
    {
        if(!isValid()) throw new IllegalStateException();
    }
}