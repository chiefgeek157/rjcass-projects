package com.rjcass.graph;

import java.util.Set;

public interface Graph extends ModelEntity
{
	Model getModel();

	void remove();

	Set<? extends Node> getNodes();

	Set<? extends Node> getNodes(NodeFilter filter);

	Set<? extends Arc> getArcs();

	Set<? extends Arc> getArcs(ArcFilter filter);

	void addListener(GraphListener listener);

	void removeListener(GraphListener listener);
}
