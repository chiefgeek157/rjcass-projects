package com.rjcass.graph.basic;

import java.util.HashSet;
import java.util.Set;

import com.rjcass.graph.Arc;
import com.rjcass.graph.Node;
import com.rjcass.graph.basic.manage.ManagedModel;
import com.rjcass.graph.basic.manage.ManagedNode;
import com.rjcass.graph.basic.manage.ModelEntityFactory;
import com.rjcass.graph.basic.manage.ModelEntityFactoryListener;

public class BasicModelEntityFactory implements ModelEntityFactory
{
	private Set<ModelEntityFactoryListener> mListeners;

	public BasicModelEntityFactory()
	{
		mListeners = new HashSet<ModelEntityFactoryListener>();
	}

	public BasicModel createModel()
	{
		ManagedModel model = doCreateModel();
		model.setFactory(this);
		fireModelCreated(model);
		return model;
	}

	public BasicGraph createGraph()
	{
		BasicGraph graph = new BasicGraph();
		fireGraphCreated(graph);
		return graph;
	}

	public Node createNode()
	{
		Node node = new BasicNode();
		fireNodeCreated(node);
		return node;
	}

	public Arc createArc()
	{
		Arc arc = new BasicArc();
		fireArcCreated(arc);
		return arc;
	}

	public void addListener(ModelEntityFactoryListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(ModelEntityFactoryListener listener)
	{
		mListeners.remove(listener);
	}

	protected ManagedModel doCreateModel()
	{
		return new BasicModel();
	}

	private void fireModelCreated(ManagedModel model)
	{
		Set<ModelEntityFactoryListener> listeners = new HashSet<ModelEntityFactoryListener>(mListeners);
		for (ModelEntityFactoryListener listener : listeners)
			listener.modelCreated(model);
	}

	private void fireGraphCreated(BasicGraph graph)
	{
		Set<ModelEntityFactoryListener> listeners = new HashSet<ModelEntityFactoryListener>(mListeners);
		for (ModelEntityFactoryListener listener : listeners)
			listener.graphCreated(graph);
	}

	private void fireNodeCreated(ManagedNode node)
	{
		Set<ModelEntityFactoryListener> listeners = new HashSet<ModelEntityFactoryListener>(mListeners);
		for (ModelEntityFactoryListener listener : listeners)
			listener.nodeCreated(node);
	}

	private void fireArcCreated(Arc arc)
	{
		Set<ModelEntityFactoryListener> listeners = new HashSet<ModelEntityFactoryListener>(mListeners);
		for (ModelEntityFactoryListener listener : listeners)
			listener.arcCreated(arc);
	}
}
