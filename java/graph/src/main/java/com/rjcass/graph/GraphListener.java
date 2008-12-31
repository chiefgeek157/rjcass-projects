package com.rjcass.graph;

public interface GraphListener
{
	void modelSet(Graph graph, Model oldModel, Model newModel);

	void nodeAdded(Graph graph, Node node);

	void nodeRemoved(Graph graph, Node node);

	void arcAdded(Graph graph, Arc arc);

	void arcRemoved(Graph graph, Arc arc);

	void removed(Graph graph);
}
