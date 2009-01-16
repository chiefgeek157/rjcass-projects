package com.rjcass.graph;

import java.util.Set;

public interface GNode extends GEntity
{
	GGraph getGraph();

	GArc joinTo(GNode node);

	GArc joinTo(GNode node, boolean directed);

	void disconnectFrom(GNode node);

	void remove();

	GArc getConnectingArc(GNode node);

	Set<? extends GArc> getArcs();

	Set<? extends GArc> getInboundArcs();

	Set<? extends GArc> getOutboundArcs();

	Set<? extends GArc> getArcs(GArcFilter filter);

	Set<? extends GArc> getArcs(GNodeFilter filter);

	Set<? extends GArc> getArcs(AdjacencyFilter filter);

	boolean isAdjacentTo(GNode node);

	Set<? extends GNode> getAdjacentNodes();

	Set<? extends GNode> getInboundNodes();

	Set<? extends GNode> getOutboundNodes();

	Set<? extends GNode> getAdjacentNodes(GArcFilter filter);

	Set<? extends GNode> getAdjacentNodes(GNodeFilter filter);

	Set<? extends GNode> getAdjacentNodes(AdjacencyFilter filter);

	void addListener(GNodeListener listener);

	void removeListener(GNodeListener listener);
}
