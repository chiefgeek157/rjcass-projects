package com.rjcass.graph;

import java.util.Set;


public interface Graph extends ModelEntity
{
	Model getModel();

	int getNodeCount();

	Set<Node> getNodes();

	void remove();

	void addListener(GraphListener listener);

	void removeListener(GraphListener listener);
}
