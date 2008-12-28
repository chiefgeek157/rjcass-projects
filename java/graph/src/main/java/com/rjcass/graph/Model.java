package com.rjcass.graph;

import java.util.Set;

public interface Model extends ModelEntity
{
	Node addNode();

	Set<? extends Graph> getGraphs();

	Set<? extends Graph> getGraphs(GraphFilter filter);

	Set<? extends Node> getNodes();

	Set<? extends Node> getNodes(NodeFilter filter);

	Set<? extends Arc> getArcs();

	Set<? extends Arc> getArcs(ArcFilter filter);

	void addListener(ModelListener listener);

	void removeListener(ModelListener listener);
}