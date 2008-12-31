package com.rjcass.graph;

public interface Arc extends ModelEntity
{
	enum Direction
	{
		UNDIRECTED, INBOUND, OUTBOUND
	};

	void setNodes(Node startNode, Node endNode);

	void setNodes(Node startNode, Node endNode, boolean undirected);

	void setUndirected();

	void setDirection(Node source, Direction direction);

	void reverse();

	void remove();

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
