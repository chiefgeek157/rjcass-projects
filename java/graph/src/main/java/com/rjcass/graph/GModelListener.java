package com.rjcass.graph;

public interface GModelListener
{
	void graphAdded(GModel model, GGraph graph);

	void graphRemoved(GModel model, GGraph graph);
	
	void graphsMerged(GModel model, GGraph source, GGraph target);
	
	void graphSplit(GModel model, GGraph source, GGraph target);
}
