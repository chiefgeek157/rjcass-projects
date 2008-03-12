package com.rjcass.graph;

public class TypeAdjacencyFilter implements AdjacencyFilter
{
    private Class<? extends Arc> mArcClass;
    private Class<? extends Node> mNodeClass;

    public TypeAdjacencyFilter(Class<? extends Arc> arcClass)
    {
        mArcClass = arcClass;
    }

    public TypeAdjacencyFilter(Class<? extends Arc> arcClass, Class<? extends Node> nodeClass)
    {
        mArcClass = arcClass;
        mNodeClass = nodeClass;
    }

    @Override public boolean passes(Arc arc, Node node)
    {
        boolean result = false;
        if((mArcClass == null || mArcClass.isInstance(arc))
                && (mNodeClass == null || mNodeClass.isInstance(node)))
            result = true;
        return result;
    }
}
