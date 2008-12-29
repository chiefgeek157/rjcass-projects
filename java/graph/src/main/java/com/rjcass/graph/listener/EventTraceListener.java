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

	public EventTraceListener()
	{
		mEvents = new ArrayList<ListenerEvent>();
	}

	public List<ListenerEvent> getEvents()
	{
		return Collections.unmodifiableList(mEvents);
	}

	public boolean compareTo(List<ListenerEvent> events)
	{
		boolean result = true;

		if (mEvents.size() == events.size())
		{
			Iterator<ListenerEvent> iter1 = mEvents.iterator();
			Iterator<ListenerEvent> iter2 = events.iterator();
			while (iter1.hasNext())
			{
				if (iter1.next() != iter2.next())
				{
					result = false;
					break;
				}
			}
		}
		else
		{
			result = false;
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
		mEvents.add(ListenerEvent.MODEL_FACTORY_MODEL_CREATED);
		model.addListener(this);
	}

	public void graphCreated(ManagedGraph graph)
	{
		mEvents.add(ListenerEvent.MANAGED_ENTITY_FACTORY_GRAPH_CREATED);
		graph.addListener(this);
	}

	public void nodeCreated(ManagedNode node)
	{
		mEvents.add(ListenerEvent.MANAGED_ENTITY_FACTORY_NODE_CREATED);
		node.addListener(this);
	}

	public void arcCreated(ManagedArc arc)
	{
		mEvents.add(ListenerEvent.MANAGED_ENTITY_FACTORY_ARC_CREATED);
		arc.addListener(this);
	}

	public void graphAdded(Model model, Graph graph)
	{
		mEvents.add(ListenerEvent.MODEL_GRAPH_ADDED);
	}

	public void graphRemoved(Model model, Graph graph)
	{
		mEvents.add(ListenerEvent.MODEL_GRAPH_REMOVED);
	}

	public void graphSplit(Model model, Graph source, Graph target)
	{
		mEvents.add(ListenerEvent.MODEL_GRAPHS_SPLIT);
	}

	public void graphsMerged(Model model, Graph source, Graph target)
	{
		mEvents.add(ListenerEvent.MODEL_GRAPHS_MERGED);
	}

	public void modelSet(Graph graph, Model oldModel, Model newModel)
	{
		mEvents.add(ListenerEvent.GRAPH_MODEL_SET);
	}

	public void nodeAdded(Graph graph, Node node)
	{
		mEvents.add(ListenerEvent.GRAPH_NODE_ADDED);
	}

	public void nodeRemoved(Graph graph, Node node)
	{
		mEvents.add(ListenerEvent.GRAPH_NODE_REMOVED);
	}

	public void removed(Graph graph)
	{
		mEvents.add(ListenerEvent.GRAPH_REMOVED);
		graph.removeListener(this);
	}

	public void graphSet(Node node, Graph oldGraph, Graph newGraph)
	{
		mEvents.add(ListenerEvent.NODE_GRAPH_SET);
	}

	public void arcAdded(Node node, Arc arc)
	{
		mEvents.add(ListenerEvent.NODE_ARC_ADDED);
	}

	public void arcRemoved(Node node, Arc arc)
	{
		mEvents.add(ListenerEvent.NODE_ARC_REMOVED);
	}

	public void removed(Node node)
	{
		mEvents.add(ListenerEvent.NODE_REMOVED);
		node.removeListener(this);
	}

	public void startNodeSet(Arc arc, Node oldNode, Node newNode)
	{
		mEvents.add(ListenerEvent.ARC_START_NODE_SET);
	}

	public void endNodeSet(Arc arc, Node oldNode, Node newNode)
	{
		mEvents.add(ListenerEvent.ARC_END_NODE_SET);
	}

	public void directedSet(Arc arc, boolean directed)
	{
		mEvents.add(ListenerEvent.ARC_DIRECTED_SET);
	}

	public void reversed(Arc arc)
	{
		mEvents.add(ListenerEvent.ARC_REVERSED);
	}

	public void removed(Arc arc)
	{
		mEvents.add(ListenerEvent.ARC_REMOVED);
		arc.removeListener(this);
	}
}
