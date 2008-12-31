package com.rjcass.graph;

public interface ArcListener
{
	void graphSet(Arc arc, Graph oldGraph, Graph newGraph);

	void nodesSet(Arc arc, Node oldStartNode, Node oldEndNode, Node newStartNode, Node newEndNode);

	void directedSet(Arc arc, boolean directed);

	void reversed(Arc arc);

	void removed(Arc arc);
}
