package com.rjcass.graph;

public interface ModelFactory
{
	Model createModel(String id);

	void addModelFactoryListener(ModelFactoryListener listener);

	void removeModelFactoryListener(ModelFactoryListener listener);
}
