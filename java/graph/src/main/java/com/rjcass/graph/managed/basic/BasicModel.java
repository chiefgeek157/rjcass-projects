package com.rjcass.graph.managed.basic;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.commons.attribute.AbstractAttributeContainer;
import com.rjcass.graph.Arc;
import com.rjcass.graph.ArcFilter;
import com.rjcass.graph.Graph;
import com.rjcass.graph.GraphFilter;
import com.rjcass.graph.ModelListener;
import com.rjcass.graph.Node;
import com.rjcass.graph.NodeFilter;
import com.rjcass.graph.Arc.Direction;
import com.rjcass.graph.managed.ManagedArc;
import com.rjcass.graph.managed.ManagedEntityFactory;
import com.rjcass.graph.managed.ManagedGraph;
import com.rjcass.graph.managed.ManagedModel;
import com.rjcass.graph.managed.ManagedNode;

public class BasicModel extends AbstractAttributeContainer implements ManagedModel
{
	private static Log sLog = LogFactory.getLog(BasicModel.class);

	private String mId;
	private String mName;
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

	public void setManagedEntityFactory(ManagedEntityFactory factory)
	{
		mEntityFactory = factory;
	}

	public boolean isValid()
	{
		return doIsValid();
	}

	public String getId()
	{
		return mId;
	}

	public String getName()
	{
		return mName;
	}

	public void setName(String name)
	{
		mName = name;
	}

	public Node addNode()
	{
		validate();

		ManagedGraph graph = createManagedGraph();
		ManagedNode node = mEntityFactory.createNode();

		graph.addManagedNode(node);

		return node;
	}

	public Set<? extends Graph> getGraphs()
	{
		validate();
		return Collections.unmodifiableSet(new HashSet<ManagedGraph>(mGraphs));
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
		return Collections.unmodifiableSet(new HashSet<ManagedNode>(mNodes));
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
		return Collections.unmodifiableSet(new HashSet<ManagedArc>(mArcs));
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

	public void setId(String id)
	{
		mId = id;
	}

	public ManagedArc addManagedArc(ManagedNode node1, ManagedNode node2, boolean directed)
	{
		ManagedArc arc = mEntityFactory.createArc();
		arc.setManagedNodes(node1, node2);

		// Combine graphs, if needed
		ManagedGraph sourceGraph = node1.getManagedGraph();
		ManagedGraph targetGraph = node2.getManagedGraph();
		if (sourceGraph != targetGraph)
		{
			if (sourceGraph.getNodes().size() > targetGraph.getNodes().size())
			{
				ManagedGraph temp = targetGraph;
				targetGraph = sourceGraph;
				sourceGraph = temp;
			}

			for (ManagedArc arcToMove : sourceGraph.getManagedArcs())
			{
				sourceGraph.removeManagedArc(arcToMove);
				targetGraph.addManagedArc(arcToMove);
			}

			for (ManagedNode nodeToMove : sourceGraph.getManagedNodes())
			{
				sourceGraph.removeManagedNode(nodeToMove);
				targetGraph.addManagedNode(nodeToMove);
			}

			fireGraphsMerged(sourceGraph, targetGraph);
		}

		targetGraph.addManagedArc(arc);

		if (directed)
			arc.setDirection(node1, Direction.OUTBOUND);

		return arc;
	}

	public void removeManagedArc(ManagedArc arc)
	{
		if (!mArcs.contains(arc))
			throw new IllegalStateException("Attempt to remove Arc that is not part of this Model");

		Set<ManagedNode> nodesToMove = new HashSet<ManagedNode>();
		Queue<ManagedNode> nodeSearchQueue = new LinkedList<ManagedNode>();

		ManagedNode node1 = arc.getStartManagedNode();
		ManagedNode node2 = arc.getEndManagedNode();

		nodeSearchQueue.add(node2);
		boolean split = true;
		while (nodeSearchQueue.size() > 0 && split)
		{
			ManagedNode currentNode = nodeSearchQueue.poll();
			nodesToMove.add(currentNode);
			for (ManagedArc arcToNeighbor : currentNode.getManagedArcs())
			{
				if (arcToNeighbor != arc)
				{
					ManagedNode neighbor = arcToNeighbor.getOtherManagedNode(currentNode);
					if (neighbor == node1)
					{
						split = false;
						break;
					}
					if (!nodesToMove.contains(neighbor) && !nodeSearchQueue.contains(neighbor))
					{
						nodeSearchQueue.add(neighbor);
					}
				}
			}
		}

		// Split Graphs if needed
		if (split)
		{
			ManagedGraph sourceGraph = node1.getManagedGraph();
			ManagedGraph targetGraph = createManagedGraph();

			Set<ManagedArc> arcsToMove = new HashSet<ManagedArc>();
			for (ManagedNode nodeToMove : nodesToMove)
			{
				arcsToMove.addAll(nodeToMove.getManagedArcs());
				sourceGraph.removeManagedNode(nodeToMove);
				targetGraph.addManagedNode(nodeToMove);
			}

			for (ManagedArc arcToMove : arcsToMove)
			{
				sourceGraph.removeManagedArc(arcToMove);
				if (arcToMove != arc)
					targetGraph.addManagedArc(arcToMove);
			}

			fireGraphsSplit(sourceGraph, targetGraph);
		}
	}

	public void managedNodeAdded(ManagedNode node)
	{
		mNodes.add(node);
	}

	public void managedNodeRemoved(ManagedNode node)
	{
		mNodes.remove(node);
	}

	public void managedArcAdded(ManagedArc arc)
	{
		mArcs.add(arc);
	}

	public void managedArcRemoved(ManagedArc arc)
	{
		mArcs.remove(arc);
	}

	public void managedGraphRemoved(ManagedGraph graph)
	{
		graph.setManagedModel(null);
		mGraphs.remove(graph);
		fireGraphRemoved(graph);
	}

	@Override
	public String toString()
	{
		return "BasicModel[" + mId + "]";
	}

	protected void validate()
	{
		if (!isValid())
			throw new IllegalStateException();
	}

	protected boolean doIsValid()
	{
		return true;
	}

	private void fireGraphAdded(ManagedGraph graph)
	{
		sLog.debug("Firing " + this + ".GraphAdded(graph:" + graph + ")");
		Set<ModelListener> listeners = new HashSet<ModelListener>(mListeners);
		for (ModelListener listener : listeners)
			listener.graphAdded(this, graph);
	}

	private void fireGraphRemoved(ManagedGraph graph)
	{
		sLog.debug("Firing " + this + ".GraphRemoved(graph:" + graph + ")");
		Set<ModelListener> listeners = new HashSet<ModelListener>(mListeners);
		for (ModelListener listener : listeners)
			listener.graphRemoved(this, graph);
	}

	private void fireGraphsMerged(ManagedGraph source, ManagedGraph target)
	{
		sLog.debug("Firing " + this + ".GraphsMerged(source:" + source + ",target:" + target + ")");
		Set<ModelListener> listeners = new HashSet<ModelListener>(mListeners);
		for (ModelListener listener : listeners)
			listener.graphsMerged(this, source, target);
	}

	private void fireGraphsSplit(ManagedGraph source, ManagedGraph target)
	{
		sLog.debug("Firing " + this + ".GraphsSplit(source:" + source + ",target:" + target + ")");
		Set<ModelListener> listeners = new HashSet<ModelListener>(mListeners);
		for (ModelListener listener : listeners)
			listener.graphSplit(this, source, target);
	}

	private ManagedGraph createManagedGraph()
	{
		ManagedGraph graph = mEntityFactory.createGraph();

		graph.setManagedModel(this);

		mGraphs.add(graph);
		fireGraphAdded(graph);

		return graph;
	}
}
