package com.rjcass.graph;

import java.util.Set;

public interface GModel extends GEntity
{
	GNode addNode();

	Set<? extends GGraph> getGraphs();

	Set<? extends GGraph> getGraphs(GGraphFilter filter);

	Set<? extends GNode> getNodes();

	Set<? extends GNode> getNodes(GNodeFilter filter);

	Set<? extends GArc> getArcs();

	Set<? extends GArc> getArcs(GArcFilter filter);

	void addListener(GModelListener listener);

	void removeListener(GModelListener listener);
}