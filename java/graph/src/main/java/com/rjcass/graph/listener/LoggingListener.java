package com.rjcass.graph.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.graph.GArc;
import com.rjcass.graph.GArcListener;
import com.rjcass.graph.GGraph;
import com.rjcass.graph.GGraphListener;
import com.rjcass.graph.GModel;
import com.rjcass.graph.GModelFactoryListener;
import com.rjcass.graph.GModelListener;
import com.rjcass.graph.GNode;
import com.rjcass.graph.GNodeListener;
import com.rjcass.graph.managed.ManagedGArc;
import com.rjcass.graph.managed.ManagedGGraph;
import com.rjcass.graph.managed.ManagedGNode;
import com.rjcass.graph.managed.ManagedGEntityFactoryListener;

public class LoggingListener implements GModelFactoryListener, ManagedGEntityFactoryListener, GModelListener, GGraphListener,
		GNodeListener, GArcListener
{
	private static Log sLog = LogFactory.getLog(LoggingListener.class);

	public LoggingListener()
	{}

	public void modelCreated(GModel model)
	{
		sLog.info("ModelFactory.modelCreated(model:" + model + ")");
		model.addListener(this);
	}

	public void graphCreated(ManagedGGraph graph)
	{
		sLog.info("ModelEntityFactory.graphCreated(graph:" + graph + ")");
		graph.addListener(this);
	}

	public void nodeCreated(ManagedGNode node)
	{
		sLog.info("ModelEntityFactory.nodeCreated(node:" + node + ")");
		node.addListener(this);
	}

	public void arcCreated(ManagedGArc arc)
	{
		sLog.info("ModelEntityFactory.arcCreated(arc:" + arc + ")");
		arc.addListener(this);
	}

	public void graphAdded(GModel model, GGraph graph)
	{
		sLog.info("GModel.graphAdded(model:" + model + ",graph:" + graph + ")");
	}

	public void graphRemoved(GModel model, GGraph graph)
	{
		sLog.info("BasicModel.graphAdded(model:" + model + ",graph:" + graph + ")");
	}

	public void graphsMerged(GModel model, GGraph source, GGraph target)
	{
		sLog.info("GModel.graphsMerged(model:" + model + ",source:" + source + ",target:" + target + ")");
	}

	public void graphSplit(GModel model, GGraph source, GGraph target)
	{
		sLog.info("GModel.graphsSplit(model:" + model + ",source:" + source + ",target:" + target + ")");
	}

	public void modelSet(GGraph graph, GModel oldModel, GModel newModel)
	{
		sLog.info("Graph.modelSet(graph:" + graph + ",oldModel:" + oldModel + ",newModel:" + newModel + ")");
	}

	public void nodeAdded(GGraph graph, GNode node)
	{
		sLog.info("Graph.nodeAdded(graph:" + graph + ",node:" + node + ")");
	}

	public void nodeRemoved(GGraph graph, GNode node)
	{
		sLog.info("Graph.nodeRemoved(graph:" + graph + ",node:" + node + ")");
	}

	public void arcAdded(GGraph graph, GArc arc)
	{
		sLog.info("Graph.arcAdded(graph:" + graph + ",arc:" + arc + ")");
	}

	public void arcRemoved(GGraph graph, GArc arc)
	{
		sLog.info("Graph.arcRemoved(graph:" + graph + ",arc:" + arc + ")");
	}

	public void removed(GGraph graph)
	{
		sLog.info("Graph.removed(graph:" + graph + ")");
	}

	public void graphSet(GNode node, GGraph oldGraph, GGraph newGraph)
	{
		sLog.info("Node.graphSet(node:" + node + ",oldGraph:" + oldGraph + ",newGraph:" + newGraph + ")");
	}

	public void arcAdded(GNode node, GArc arc)
	{
		sLog.info("Node.arcAdded(node:" + node + ",arc:" + arc + ")");
	}

	public void arcRemoved(GNode node, GArc arc)
	{
		sLog.info("Node.arcRemoved(node:" + node + ",arc:" + arc + ")");
	}

	public void removed(GNode node)
	{
		sLog.info("Node.removed(node:" + node + ")");
	}

	public void graphSet(GArc arc, GGraph oldGraph, GGraph newGraph)
	{
		sLog.info("Arc.graphSet(arc:" + arc + ",oldGraph:" + oldGraph + ",newGraph:" + newGraph + ")");
	}

	public void nodesSet(GArc arc, GNode oldStartNode, GNode oldEndNode, GNode newStartNode, GNode newEndNode)
	{
		sLog.info("Arc.nodesSet(arc:" + arc + ",oldStartNode:" + oldStartNode + ",oldEndNode:" + oldEndNode + ",newStartNode:"
				+ newStartNode + ",newEndNode:" + newEndNode + ")");
	}

	public void directedSet(GArc arc, boolean directed)
	{
		sLog.info("Arc.directedSet(arc:" + arc + ",directed:" + directed + ")");
	}

	public void reversed(GArc arc)
	{
		sLog.info("Arc.reversed(arc:" + arc + ")");
	}

	public void removed(GArc arc)
	{
		sLog.info("Arc.removed(arc:" + arc + ")");
	}
}
