package com.rjcass.graph.listener;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
import com.rjcass.graph.managed.ManagedEntityFactoryListener;
import com.rjcass.graph.managed.ManagedGraph;
import com.rjcass.graph.managed.ManagedNode;

public class EventTraceListener implements ModelFactoryListener, ManagedEntityFactoryListener, ModelListener, GraphListener,
		NodeListener, ArcListener
{
	private List<ListenerEvent> mEvents;
	private boolean mPaused;

	public EventTraceListener()
	{
		mEvents = new ArrayList<ListenerEvent>();
	}

	public void addEvent(ListenerEventType type, Object... objs)
	{
		mEvents.add(new ListenerEvent(type, objs));
	}

	public List<ListenerEvent> getEvents()
	{
		return Collections.unmodifiableList(mEvents);
	}

	public void pause()
	{
		mPaused = true;
	}

	public void resume()
	{
		mPaused = false;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;

		if (obj != null && obj instanceof EventTraceListener)
		{
			EventTraceListener listener = (EventTraceListener)obj;
			if (mEvents.size() == listener.mEvents.size())
			{
				result = true;
				Iterator<ListenerEvent> iter1 = mEvents.iterator();
				Iterator<ListenerEvent> iter2 = listener.mEvents.iterator();
				while (iter1.hasNext())
				{
					if (!iter1.next().equals(iter2.next()))
					{
						result = false;
						break;
					}
				}
			}
		}

		return result;
	}

	public void dump(OutputStream os)
	{
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
		for (ListenerEvent event : mEvents)
			pw.println(event);
	}

	public void modelCreated(Model model)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MODEL_FACTORY_MODEL_CREATED, model);
		model.addListener(this);
	}

	public void graphCreated(ManagedGraph graph)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_GRAPH_CREATED, graph);
		graph.addListener(this);
	}

	public void nodeCreated(ManagedNode node)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_NODE_CREATED, node);
		node.addListener(this);
	}

	public void arcCreated(ManagedArc arc)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_ARC_CREATED, arc);
		arc.addListener(this);
	}

	public void graphAdded(Model model, Graph graph)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MODEL_GRAPH_ADDED, model, graph);
	}

	public void graphRemoved(Model model, Graph graph)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MODEL_GRAPH_REMOVED, model, graph);
	}

	public void graphSplit(Model model, Graph source, Graph target)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MODEL_GRAPH_SPLIT, model, source, target);
	}

	public void graphsMerged(Model model, Graph source, Graph target)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MODEL_GRAPHS_MERGED, model, source, target);
	}

	public void modelSet(Graph graph, Model oldModel, Model newModel)
	{
		if (!mPaused)
			addEvent(ListenerEventType.GRAPH_MODEL_SET, graph, oldModel, newModel);
	}

	public void nodeAdded(Graph graph, Node node)
	{
		if (!mPaused)
			addEvent(ListenerEventType.GRAPH_NODE_ADDED, graph, node);
	}

	public void nodeRemoved(Graph graph, Node node)
	{
		if (!mPaused)
			addEvent(ListenerEventType.GRAPH_NODE_REMOVED, graph, node);
	}

	public void arcAdded(Graph graph, Arc arc)
	{
		if (!mPaused)
			addEvent(ListenerEventType.GRAPH_ARC_ADDED, graph, arc);
	}

	public void arcRemoved(Graph graph, Arc arc)
	{
		if (!mPaused)
			addEvent(ListenerEventType.GRAPH_ARC_REMOVED, graph, arc);
	}

	public void removed(Graph graph)
	{
		if (!mPaused)
			addEvent(ListenerEventType.GRAPH_REMOVED, graph);
		graph.removeListener(this);
	}

	public void graphSet(Node node, Graph oldGraph, Graph newGraph)
	{
		if (!mPaused)
			addEvent(ListenerEventType.NODE_GRAPH_SET, node, oldGraph, newGraph);
	}

	public void arcAdded(Node node, Arc arc)
	{
		if (!mPaused)
			addEvent(ListenerEventType.NODE_ARC_ADDED, node, arc);
	}

	public void arcRemoved(Node node, Arc arc)
	{
		if (!mPaused)
			addEvent(ListenerEventType.NODE_ARC_REMOVED, node, arc);
	}

	public void removed(Node node)
	{
		if (!mPaused)
			addEvent(ListenerEventType.NODE_REMOVED, node);
		node.removeListener(this);
	}

	public void graphSet(Arc arc, Graph oldGraph, Graph newGraph)
	{
		if (!mPaused)
			addEvent(ListenerEventType.ARC_GRAPH_SET, arc, oldGraph, newGraph);
	}

	public void nodesSet(Arc arc, Node oldStartNode, Node oldEndNode, Node newStartNode, Node newEndNode)
	{
		if (!mPaused)
			addEvent(ListenerEventType.ARC_NODES_SET, arc, oldStartNode, oldEndNode, newStartNode, newEndNode);
	}

	public void directedSet(Arc arc, boolean directed)
	{
		if (!mPaused)
			addEvent(ListenerEventType.ARC_DIRECTED_SET, arc, directed);
	}

	public void reversed(Arc arc)
	{
		if (!mPaused)
			addEvent(ListenerEventType.ARC_REVERSED, arc);
	}

	public void removed(Arc arc)
	{
		if (!mPaused)
			addEvent(ListenerEventType.ARC_REMOVED, arc);
		arc.removeListener(this);
	}
}
