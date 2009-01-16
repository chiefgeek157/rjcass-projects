package com.rjcass.graph;

public interface GModelFactory
{
	GModel createModel();

	void addModelFactoryListener(GModelFactoryListener listener);

	void removeModelFactoryListener(GModelFactoryListener listener);
}
