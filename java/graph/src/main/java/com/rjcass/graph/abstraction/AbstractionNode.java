package com.rjcass.graph.abstraction;

import java.util.Set;

import com.rjcass.graph.Node;

public class AbstractionNode extends Node
{
    private AbstractionLayer mLayer;
    private AbstractionNode mAbstraction;
    private Set<AbstractionNode> mDetails;
    
    public AbstractionNode()
    {
    }
}
