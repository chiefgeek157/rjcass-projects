package com.rjcass.graph.listener;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
import com.rjcass.graph.managed.ManagedGEntityFactoryListener;
import com.rjcass.graph.managed.ManagedGGraph;
import com.rjcass.graph.managed.ManagedGNode;

public class EventTraceListener implements GModelFactoryListener, ManagedGEntityFactoryListener, GModelListener, GGraphListener,
		GNodeListener, GArcListener
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

	public void modelCreated(GModel model)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MODEL_FACTORY_MODEL_CREATED, model);
		model.addListener(this);
	}

	public void graphCreated(ManagedGGraph graph)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_GRAPH_CREATED, graph);
		graph.addListener(this);
	}

	public void nodeCreated(ManagedGNode node)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_NODE_CREATED, node);
		node.addListener(this);
	}

	public void arcCreated(ManagedGArc arc)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MANAGED_ENTITY_FACTORY_ARC_CREATED, arc);
		arc.addListener(this);
	}

	public void graphAdded(GModel model, GGraph graph)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MODEL_GRAPH_ADDED, model, graph);
	}

	public void graphRemoved(GModel model, GGraph graph)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MODEL_GRAPH_REMOVED, model, graph);
	}

	public void graphSplit(GModel model, GGraph source, GGraph target)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MODEL_GRAPH_SPLIT, model, source, target);
	}

	public void graphsMerged(GModel model, GGraph source, GGraph target)
	{
		if (!mPaused)
			addEvent(ListenerEventType.MODEL_GRAPHS_MERGED, model, source, target);
	}

	public void modelSet(GGraph graph, GModel oldModel, GModel newModel)
	{
		if (!mPaused)
			addEvent(ListenerEventType.GRAPH_MODEL_SET, graph, oldModel, newModel);
	}

	public void nodeAdded(GGraph graph, GNode node)
	{
		if (!mPaused)
			addEvent(ListenerEventType.GRAPH_NODE_ADDED, graph, node);
	}

	public void nodeRemoved(GGraph graph, GNode node)
	{
		if (!mPaused)
			addEvent(ListenerEventType.GRAPH_NODE_REMOVED, graph, node);
	}

	public void arcAdded(GGraph graph, GArc arc)
	{
		if (!mPaused)
			addEvent(ListenerEventType.GRAPH_ARC_ADDED, graph, arc);
	}

	public void arcRemoved(GGraph graph, GArc arc)
	{
		if (!mPaused)
			addEvent(ListenerEventType.GRAPH_ARC_REMOVED, graph, arc);
	}

	public void removed(GGraph graph)
	{
		if (!mPaused)
			addEvent(ListenerEventType.GRAPH_REMOVED, graph);
		graph.removeListener(this);
	}

	public void graphSet(GNode node, GGraph oldGraph, GGraph newGraph)
	{
		if (!mPaused)
			addEvent(ListenerEventType.NODE_GRAPH_SET, node, oldGraph, newGraph);
	}

	public void arcAdded(GNode node, GArc arc)
	{
		if (!mPaused)
			addEvent(ListenerEventType.NODE_ARC_ADDED, node, arc);
	}

	public void arcRemoved(GNode node, GArc arc)
	{
		if (!mPaused)
			addEvent(ListenerEventType.NODE_ARC_REMOVED, node, arc);
	}

	public void removed(GNode node)
	{
		if (!mPaused)
			addEvent(ListenerEventType.NODE_REMOVED, node);
		node.removeListener(this);
	}

	public void graphSet(GArc arc, GGraph oldGraph, GGraph newGraph)
	{
		if (!mPaused)
			addEvent(ListenerEventType.ARC_GRAPH_SET, arc, oldGraph, newGraph);
	}

	public void nodesSet(GArc arc, GNode oldStartNode, GNode oldEndNode, GNode newStartNode, GNode newEndNode)
	{
		if (!mPaused)
			addEvent(ListenerEventType.ARC_NODES_SET, arc, oldStartNode, oldEndNode, newStartNode, newEndNode);
	}

	public void directedSet(GArc arc, boolean directed)
	{
		if (!mPaused)
			addEvent(ListenerEventType.ARC_DIRECTED_SET, arc, directed);
	}

	public void reversed(GArc arc)
	{
		if (!mPaused)
			addEvent(ListenerEventType.ARC_REVERSED, arc);
	}

	public void removed(GArc arc)
	{
		if (!mPaused)
			addEvent(ListenerEventType.ARC_REMOVED, arc);
		arc.removeListener(this);
	}
}
