package com.rjcass.graph;

public interface GArc extends GEntity
{
	enum Direction
	{
		UNDIRECTED, INBOUND, OUTBOUND
	};

	void setUndirected();

	void setDirection(GNode source, Direction direction);

	void reverse();

	void remove();

	GGraph getGraph();

	boolean isConnectedTo(GNode node);

	GNode getStartNode();

	GNode getEndNode();

	GNode getOtherNode(GNode node);

	boolean isDirected();

	Direction getDirection(GNode node);

	void addListener(GArcListener listener);

	void removeListener(GArcListener listener);
}
