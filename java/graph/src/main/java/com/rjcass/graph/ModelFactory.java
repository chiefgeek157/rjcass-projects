package com.rjcass.graph;

public interface ModelFactory
{
	Model createModel();

	void addModelFactoryListener(ModelFactoryListener listener);

	void removeModelFactoryListener(ModelFactoryListener listener);
}
