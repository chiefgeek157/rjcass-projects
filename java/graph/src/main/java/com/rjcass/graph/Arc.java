package com.rjcass.graph;

public interface Arc extends ModelEntity
{
	enum Direction
	{
		UNDIRECTED, INBOUND, OUTBOUND
	};

	boolean isConnectedTo(Node node);
	
	Node getStartNode();

	Node getEndNode();

	Node getOtherNode(Node node);

	boolean isDirected();

	Direction getDirection(Node node);

	void setUndirected();
	
	void setDirection(Node source, Direction direction);

	void reverse();

	void remove();

	void addListener(ArcListener listener);

	void removeListener(ArcListener listener);
}
