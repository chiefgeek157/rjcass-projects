package com.rjcass.depends.old.basic;

import com.rjcass.depends.old.builder.BuildableModel;
import com.rjcass.depends.old.builder.BuildableModelFactory;

public class BasicModelFactory implements BuildableModelFactory
{
    public BasicModelFactory()
    {
    }

    public BuildableModel newInstance()
    {
        return new BasicModel();
    }
}
