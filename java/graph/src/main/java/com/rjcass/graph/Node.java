package com.rjcass.graph;

import java.util.Set;

public interface Node extends ModelEntity
{
	Graph getGraph();

	Arc joinTo(Node node);

	Arc joinTo(Node node, boolean directed);

	void disconnectFrom(Node node);

	void remove();

	Arc getConnectingArc(Node node);

	Set<? extends Arc> getArcs();
	
	Set<? extends Arc> getInboundArcs();

	Set<? extends Arc> getOutboundArcs();
	
	Set<? extends Arc> getArcs(ArcFilter filter);

	Set<? extends Arc> getArcs(NodeFilter filter);

	Set<? extends Arc> getArcs(AdjacencyFilter filter);

	boolean isAdjacentTo(Node node);

	Set<? extends Node> getAdjacentNodes();

	Set<? extends Node> getInboundNodes();

	Set<? extends Node> getOutboundNodes();
	
	Set<? extends Node> getAdjacentNodes(ArcFilter filter);

	Set<? extends Node> getAdjacentNodes(NodeFilter filter);

	Set<? extends Node> getAdjacentNodes(AdjacencyFilter filter);

	void addListener(NodeListener listener);

	void removeListener(NodeListener listener);
}
