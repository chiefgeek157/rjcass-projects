package com.rjcass.graph.basic;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import com.rjcass.graph.AbstractModelEntity;
import com.rjcass.graph.Arc;
import com.rjcass.graph.ArcFilter;
import com.rjcass.graph.Graph;
import com.rjcass.graph.GraphFilter;
import com.rjcass.graph.ModelListener;
import com.rjcass.graph.Node;
import com.rjcass.graph.NodeFilter;
import com.rjcass.graph.managed.ManagedArc;
import com.rjcass.graph.managed.ManagedGraph;
import com.rjcass.graph.managed.ManagedModel;
import com.rjcass.graph.managed.ManagedNode;
import com.rjcass.graph.managed.ManagedEntityFactory;

public class BasicModel extends AbstractModelEntity implements ManagedModel
{
	private ManagedEntityFactory mEntityFactory;
	private Set<ManagedGraph> mGraphs;
	private Set<ManagedNode> mNodes;
	private Set<ManagedArc> mArcs;
	private Set<ModelListener> mListeners;

	public BasicModel()
	{
		mGraphs = new HashSet<ManagedGraph>();
		mNodes = new HashSet<ManagedNode>();
		mArcs = new HashSet<ManagedArc>();
		mListeners = new HashSet<ModelListener>();
	}

	public void setEntityFactory(ManagedEntityFactory factory)
	{
		mEntityFactory = factory;
	}

	public boolean isValid()
	{
		return true;
	}

	public Node addNode()
	{
		validate();

		ManagedGraph graph = mEntityFactory.createGraph();

		mGraphs.add(graph);
		fireGraphAdded(graph);

		graph.setModel(this);

		ManagedNode node = mEntityFactory.createNode();

		mNodes.add(node);
		graph.addNode(node);

		return node;
	}

	public Set<? extends Graph> getGraphs()
	{
		validate();
		return Collections.unmodifiableSet(mGraphs);
	}

	public Set<? extends Graph> getGraphs(GraphFilter filter)
	{
		validate();
		Set<ManagedGraph> graphs = new HashSet<ManagedGraph>();
		for (ManagedGraph graph : mGraphs)
		{
			if (filter.passes(graph))
				graphs.add(graph);
		}
		return Collections.unmodifiableSet(graphs);
	}

	public Set<? extends Node> getNodes()
	{
		validate();
		return Collections.unmodifiableSet(mNodes);
	}

	public Set<? extends Node> getNodes(NodeFilter filter)
	{
		validate();
		Set<ManagedNode> nodes = new HashSet<ManagedNode>();
		for (ManagedNode node : mNodes)
		{
			if (filter.passes(node))
				nodes.add(node);
		}
		return Collections.unmodifiableSet(nodes);
	}

	public Set<? extends Arc> getArcs()
	{
		validate();
		return Collections.unmodifiableSet(mArcs);
	}

	public Set<? extends Arc> getArcs(ArcFilter filter)
	{
		validate();
		Set<ManagedArc> arcs = new HashSet<ManagedArc>();
		for (ManagedArc arc : mArcs)
		{
			if (filter.passes(arc))
				arcs.add(arc);
		}
		return Collections.unmodifiableSet(arcs);
	}

	public void addListener(ModelListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(ModelListener listener)
	{
		mListeners.remove(listener);
	}

	public ManagedArc addArc(ManagedNode node1, ManagedNode node2)
	{
		validate();

		ManagedArc arc = mEntityFactory.createArc();
		mArcs.add(arc);

		arc.setStartNode(node1);
		arc.setEndNode(node2);

		// Combine graphs, if needed
		ManagedGraph graph1 = node1.getManagedGraph();
		ManagedGraph graph2 = node2.getManagedGraph();
		if (graph1 != graph2)
		{
			ManagedGraph source, target;
			if (graph2.getNodes().size() > graph1.getNodes().size())
			{
				target = graph2;
				source = graph1;
			}
			else
			{
				target = graph1;
				source = graph2;
			}
			for (ManagedNode node : source.getManagedNodes())
			{
				source.removeNode(node);
				target.addNode(node);
			}
		}
		return arc;
	}

	public void removeGraph(ManagedGraph graph)
	{
		if (graph.getModel() != this)
			throw new IllegalStateException("Attempt to remove Graph that is not part of this Model");

		graph.setModel(null);

		mGraphs.remove(graph);
		fireGraphRemoved(graph);
	}

	public void removeNode(ManagedNode node)
	{
		if (!mNodes.remove(node))
			throw new IllegalStateException("Attempt to remove Node that is not part of this Model");
	}

	public boolean removeArc(ManagedArc arc)
	{
		if (!mArcs.contains(arc))
			throw new IllegalStateException("Attempt to remove Arc that is not part of this Model");
		
		Set<ManagedNode> moveSet = new HashSet<ManagedNode>();
		Queue<ManagedNode> queue = new LinkedList<ManagedNode>();

		ManagedNode node1 = arc.getStartManagedNode();
		ManagedNode node2 = arc.getEndManagedNode();
		
		queue.add(node2);
		boolean split = true;
		while (queue.size() > 0 && split)
		{
			ManagedNode currentNode = queue.poll();
			moveSet.add(currentNode);
			for (ManagedNode neighbor : currentNode.getAdjacentManagedNodes())
			{
				if (neighbor == node1)
				{
					split = false;
					break;
				}
				if (!moveSet.contains(neighbor) && !queue.contains(neighbor))
				{
					queue.add(neighbor);
				}
			}
		}

		if (split)
		{
			ManagedGraph source = node1.getManagedGraph();
			ManagedGraph target = mEntityFactory.createGraph();

			mGraphs.add(target);
			fireGraphAdded(target);
			
			target.setModel(this);

			for (ManagedNode node : moveSet)
			{
				source.removeNode(node);
				target.addNode(node);
			}
		}

		return split;
	}

	private void fireGraphAdded(ManagedGraph graph)
	{
		Set<ModelListener> listeners = new HashSet<ModelListener>(mListeners);
		for (ModelListener listener : listeners)
			listener.graphAdded(this, graph);
	}

	private void fireGraphRemoved(ManagedGraph graph)
	{
		Set<ModelListener> listeners = new HashSet<ModelListener>(mListeners);
		for (ModelListener listener : listeners)
			listener.graphRemoved(this, graph);
	}
}
