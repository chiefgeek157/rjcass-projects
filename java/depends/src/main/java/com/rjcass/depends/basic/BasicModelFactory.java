package com.rjcass.depends.basic;

import com.rjcass.depends.builder.BuildableModel;
import com.rjcass.depends.builder.BuildableModelFactory;

public class BasicModelFactory implements BuildableModelFactory
{
    public BasicModelFactory()
    {
    }

    @Override
    public BuildableModel newInstance()
    {
        return new BasicModel();
    }
}
