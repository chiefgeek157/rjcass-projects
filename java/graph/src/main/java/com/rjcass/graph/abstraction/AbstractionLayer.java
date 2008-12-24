package com.rjcass.graph.abstraction;

import java.util.Set;

import com.rjcass.graph.Node;

public class AbstractionLayer extends Node
{
    private String mName;
    private Set<AbstractionNode> mNodes;
    
    public AbstractionLayer ()
    {
    }
}
