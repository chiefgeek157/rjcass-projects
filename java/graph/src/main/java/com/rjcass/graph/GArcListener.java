package com.rjcass.graph;

public interface GArcListener
{
	void graphSet(GArc arc, GGraph oldGraph, GGraph newGraph);

	void nodesSet(GArc arc, GNode oldStartNode, GNode oldEndNode, GNode newStartNode, GNode newEndNode);

	void directedSet(GArc arc, boolean directed);

	void reversed(GArc arc);

	void removed(GArc arc);
}
