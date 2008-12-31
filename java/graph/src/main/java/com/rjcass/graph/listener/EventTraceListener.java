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

	public void addEvent(ListenerEvent event)
	{
		mEvents.add(event);
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
					if (iter1.next() != iter2.next())
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
			mEvents.add(ListenerEvent.MODEL_FACTORY_MODEL_CREATED);
		model.addListener(this);
	}

	public void graphCreated(ManagedGraph graph)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.MANAGED_ENTITY_FACTORY_GRAPH_CREATED);
		graph.addListener(this);
	}

	public void nodeCreated(ManagedNode node)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.MANAGED_ENTITY_FACTORY_NODE_CREATED);
		node.addListener(this);
	}

	public void arcCreated(ManagedArc arc)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.MANAGED_ENTITY_FACTORY_ARC_CREATED);
		arc.addListener(this);
	}

	public void graphAdded(Model model, Graph graph)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.MODEL_GRAPH_ADDED);
	}

	public void graphRemoved(Model model, Graph graph)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.MODEL_GRAPH_REMOVED);
	}

	public void graphSplit(Model model, Graph source, Graph target)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.MODEL_GRAPH_SPLIT);
	}

	public void graphsMerged(Model model, Graph source, Graph target)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.MODEL_GRAPHS_MERGED);
	}

	public void modelSet(Graph graph, Model oldModel, Model newModel)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.GRAPH_MODEL_SET);
	}

	public void nodeAdded(Graph graph, Node node)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.GRAPH_NODE_ADDED);
	}

	public void nodeRemoved(Graph graph, Node node)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.GRAPH_NODE_REMOVED);
	}

	public void arcAdded(Graph graph, Arc arc)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.GRAPH_ARC_ADDED);
	}

	public void arcRemoved(Graph graph, Arc arc)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.GRAPH_ARC_REMOVED);
	}

	public void removed(Graph graph)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.GRAPH_REMOVED);
		graph.removeListener(this);
	}

	public void graphSet(Node node, Graph oldGraph, Graph newGraph)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.NODE_GRAPH_SET);
	}

	public void arcAdded(Node node, Arc arc)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.NODE_ARC_ADDED);
	}

	public void arcRemoved(Node node, Arc arc)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.NODE_ARC_REMOVED);
	}

	public void removed(Node node)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.NODE_REMOVED);
		node.removeListener(this);
	}

	public void graphSet(Arc arc, Graph oldGraph, Graph newGraph)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.ARC_GRAPH_SET);
	}

	public void nodesSet(Arc arc, Node oldStartNode, Node oldEndNode, Node newStartNode, Node newEndNode)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.ARC_NODES_SET);
	}

	public void directedSet(Arc arc, boolean directed)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.ARC_DIRECTED_SET);
	}

	public void reversed(Arc arc)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.ARC_REVERSED);
	}

	public void removed(Arc arc)
	{
		if (!mPaused)
			mEvents.add(ListenerEvent.ARC_REMOVED);
		arc.removeListener(this);
	}
}
