package com.rjcass.graph.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.graph.Arc;
import com.rjcass.graph.ArcListener;
import com.rjcass.graph.Graph;
import com.rjcass.graph.GraphListener;
import com.rjcass.graph.Model;
import com.rjcass.graph.ModelFactoryListener;
import com.rjcass.graph.ModelListener;
import com.rjcass.graph.Node;
import com.rjcass.graph.NodeListener;
import com.rjcass.graph.managed.ManagedArc;
import com.rjcass.graph.managed.ManagedGraph;
import com.rjcass.graph.managed.ManagedNode;
import com.rjcass.graph.managed.ModelEntityFactoryListener;

public class LoggingListener implements ModelFactoryListener, ModelEntityFactoryListener, ModelListener, GraphListener,
		NodeListener, ArcListener
{
	private static Log sLog = LogFactory.getLog(LoggingListener.class);

	public LoggingListener()
	{}

	public void modelCreated(Model model)
	{
		sLog.info("ModelFactory.modelCreated(model:" + model + ")");
		model.addListener(this);
	}

	public void graphCreated(ManagedGraph graph)
	{
		sLog.info("ModelEntityFactory.graphCreated(graph:" + graph + ")");
		graph.addListener(this);
	}

	public void nodeCreated(ManagedNode node)
	{
		sLog.info("ModelEntityFactory.nodeCreated(node:" + node + ")");
		node.addListener(this);
	}

	public void arcCreated(ManagedArc arc)
	{
		sLog.info("ModelEntityFactory.arcCreated(arc:" + arc + ")");
		arc.addListener(this);
	}

	public void graphAdded(Model model, Graph graph)
	{
		sLog.info("Model.graphAdded(model:" + model + ",graph:" + graph + ")");
	}

	public void graphRemoved(Model model, Graph graph)
	{
		sLog.info("BasicModel.graphAdded(model:" + model + ",graph:" + graph + ")");
	}

	public void graphsMerged(Model model, Graph source, Graph target)
	{
		sLog.info("Model.graphsMerged(model:" + model + ",source:" + source + ",target:" + target + ")");
	}

	public void graphSplit(Model model, Graph source, Graph target)
	{
		sLog.info("Model.graphsSplit(model:" + model + ",source:" + source + ",target:" + target + ")");
	}

	public void modelSet(Graph graph, Model oldModel, Model newModel)
	{
		sLog.info("Graph.modelSet(graph:" + graph + ",oldModel:" + oldModel + ",newModel:" + newModel + ")");
	}

	public void nodeAdded(Graph graph, Node node)
	{
		sLog.info("Graph.nodeAdded(graph:" + graph + ",node:" + node + ")");
	}

	public void nodeRemoved(Graph graph, Node node)
	{
		sLog.info("Graph.nodeRemoved(graph:" + graph + ",node:" + node + ")");
	}

	public void removed(Graph graph)
	{
		sLog.info("Graph.removed(graph:" + graph + ")");
	}

	public void graphSet(Node node, Graph oldGraph, Graph newGraph)
	{
		sLog.info("Node.graphSet(node:" + node + ",oldGraph:" + oldGraph + ",newGraph:" + newGraph + ")");
	}

	public void arcAdded(Node node, Arc arc)
	{
		sLog.info("Node.arcAdded(node:" + node + ",arc:" + arc + ")");
	}

	public void arcRemoved(Node node, Arc arc)
	{
		sLog.info("Node.arcRemoved(node:" + node + ",arc:" + arc + ")");
	}

	public void removed(Node node)
	{
		sLog.info("Node.removed(node:" + node + ")");
	}

	public void startNodeSet(Arc arc, Node oldNode, Node newNode)
	{
		sLog.info("Arc.startNodeSet(arc:" + arc + ",oldNode:" + oldNode + ",newNode:" + newNode + ")");
	}

	public void endNodeSet(Arc arc, Node oldNode, Node newNode)
	{
		sLog.info("Arc.endNodeSet(arc:" + arc + ",oldNode:" + oldNode + ",newNode:" + newNode + ")");
	}

	public void directedSet(Arc arc, boolean directed)
	{
		sLog.info("Arc.directedSet(arc:" + arc + ",directed:" + directed + ")");
	}

	public void reversed(Arc arc)
	{
		sLog.info("Arc.reversed(arc:" + arc + ")");
	}

	public void removed(Arc arc)
	{
		sLog.info("Arc.removed(arc:" + arc + ")");
	}
}
