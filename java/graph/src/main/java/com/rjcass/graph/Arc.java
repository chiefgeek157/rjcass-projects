package com.rjcass.graph;

public interface Arc
{
	enum Direction
	{
		UNDIRECTED, INBOUND, OUTBOUND
	};

	void setUndirected();

	void setDirection(Node source, Direction direction);

	void reverse();

	void remove();

	boolean isValid();

	String getId();

	Graph getGraph();

	boolean isConnectedTo(Node node);

	Node getStartNode();

	Node getEndNode();

	Node getOtherNode(Node node);

	boolean isDirected();

	Direction getDirection(Node node);

	void addListener(ArcListener listener);

	void removeListener(ArcListener listener);
}
