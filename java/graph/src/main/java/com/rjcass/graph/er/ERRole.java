package com.rjcass.graph.er;

import com.rjcass.graph.Node;

public class ERRole extends Node
{
    private String mName;

    public ERRole()
    {
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }
}
