package com.rjcass.graph;

import java.util.Set;


public interface Model extends ModelEntity
{
	Set<? extends Graph> getGraphs();

	Node addNode();

	Arc addArc(Node startNode, Node endNode);

	void addListener(ModelListener listener);

	void removeListener(ModelListener listener);
}