package com.rjcass.graph;

import java.util.Set;

public interface GGraph extends GEntity
{
	void remove();

	GModel getModel();

	Set<? extends GNode> getNodes();

	Set<? extends GNode> getNodes(GNodeFilter filter);

	Set<? extends GArc> getArcs();

	Set<? extends GArc> getArcs(GArcFilter filter);

	void addListener(GGraphListener listener);

	void removeListener(GGraphListener listener);
}
