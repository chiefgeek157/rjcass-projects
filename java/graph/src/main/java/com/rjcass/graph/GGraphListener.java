package com.rjcass.graph;

public interface GGraphListener
{
	void modelSet(GGraph graph, GModel oldModel, GModel newModel);

	void nodeAdded(GGraph graph, GNode node);

	void nodeRemoved(GGraph graph, GNode node);

	void arcAdded(GGraph graph, GArc arc);

	void arcRemoved(GGraph graph, GArc arc);

	void removed(GGraph graph);
}
