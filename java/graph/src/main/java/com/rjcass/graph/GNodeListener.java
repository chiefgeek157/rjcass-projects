package com.rjcass.graph;

public interface GNodeListener
{
	void graphSet(GNode node, GGraph oldGraph, GGraph newGraph);

	void arcAdded(GNode node, GArc arc);

	void arcRemoved(GNode node, GArc arc);

	void removed(GNode node);
}
