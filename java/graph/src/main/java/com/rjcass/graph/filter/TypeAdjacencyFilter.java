package com.rjcass.graph.filter;

import com.rjcass.graph.AdjacencyFilter;
import com.rjcass.graph.GArc;
import com.rjcass.graph.GNode;

public class TypeAdjacencyFilter implements AdjacencyFilter
{
    private Class<? extends GArc> mArcClass;
    private Class<? extends GNode> mNodeClass;

    public TypeAdjacencyFilter(Class<? extends GArc> arcClass)
    {
        mArcClass = arcClass;
    }

    public TypeAdjacencyFilter(Class<? extends GArc> arcClass, Class<? extends GNode> nodeClass)
    {
        mArcClass = arcClass;
        mNodeClass = nodeClass;
    }

	public boolean passes(GArc arc, GNode node)
    {
        boolean result = false;
        if((mArcClass == null || mArcClass.isInstance(arc))
                && (mNodeClass == null || mNodeClass.isInstance(node)))
            result = true;
        return result;
    }
}
