package com.rjcass.graph;

public interface ArcListener
{
	void startNodeSet(Arc arc, Node oldNode, Node newNode);

	void endNodeSet(Arc arc, Node oldNode, Node newNode);

	void directedSet(Arc arc, boolean directed);

	void reversed(Arc arc);

	void removed(Arc arc);
}
