package com.rjcass.graph.managed.basic;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.commons.attribute.AbstractAttributeContainer;
import com.rjcass.graph.GArc;
import com.rjcass.graph.GArcFilter;
import com.rjcass.graph.GGraph;
import com.rjcass.graph.GGraphFilter;
import com.rjcass.graph.GModelListener;
import com.rjcass.graph.GNode;
import com.rjcass.graph.GNodeFilter;
import com.rjcass.graph.GArc.Direction;
import com.rjcass.graph.managed.ManagedGArc;
import com.rjcass.graph.managed.ManagedGEntityFactory;
import com.rjcass.graph.managed.ManagedGGraph;
import com.rjcass.graph.managed.ManagedGModel;
import com.rjcass.graph.managed.ManagedGNode;

public class BasicGModel extends AbstractAttributeContainer implements ManagedGModel
{
	private static Log sLog = LogFactory.getLog(BasicGModel.class);

	private String mId;
	private String mName;
	private ManagedGEntityFactory mEntityFactory;
	private Set<ManagedGGraph> mGraphs;
	private Set<ManagedGNode> mNodes;
	private Set<ManagedGArc> mArcs;
	private Set<GModelListener> mListeners;

	public BasicGModel()
	{
		mGraphs = new HashSet<ManagedGGraph>();
		mNodes = new HashSet<ManagedGNode>();
		mArcs = new HashSet<ManagedGArc>();
		mListeners = new HashSet<GModelListener>();
	}

	public void setManagedEntityFactory(ManagedGEntityFactory factory)
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

	public GNode addNode()
	{
		validate();

		ManagedGGraph graph = createManagedGraph();
		ManagedGNode node = mEntityFactory.createNode();

		graph.addManagedNode(node);

		return node;
	}

	public Set<? extends GGraph> getGraphs()
	{
		validate();
		return Collections.unmodifiableSet(new HashSet<ManagedGGraph>(mGraphs));
	}

	public Set<? extends GGraph> getGraphs(GGraphFilter filter)
	{
		validate();
		Set<ManagedGGraph> graphs = new HashSet<ManagedGGraph>();
		for (ManagedGGraph graph : mGraphs)
		{
			if (filter.passes(graph))
				graphs.add(graph);
		}
		return Collections.unmodifiableSet(graphs);
	}

	public Set<? extends GNode> getNodes()
	{
		validate();
		return Collections.unmodifiableSet(new HashSet<ManagedGNode>(mNodes));
	}

	public Set<? extends GNode> getNodes(GNodeFilter filter)
	{
		validate();
		Set<ManagedGNode> nodes = new HashSet<ManagedGNode>();
		for (ManagedGNode node : mNodes)
		{
			if (filter.passes(node))
				nodes.add(node);
		}
		return Collections.unmodifiableSet(nodes);
	}

	public Set<? extends GArc> getArcs()
	{
		validate();
		return Collections.unmodifiableSet(new HashSet<ManagedGArc>(mArcs));
	}

	public Set<? extends GArc> getArcs(GArcFilter filter)
	{
		validate();
		Set<ManagedGArc> arcs = new HashSet<ManagedGArc>();
		for (ManagedGArc arc : mArcs)
		{
			if (filter.passes(arc))
				arcs.add(arc);
		}
		return Collections.unmodifiableSet(arcs);
	}

	public void addListener(GModelListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(GModelListener listener)
	{
		mListeners.remove(listener);
	}

	public void setId(String id)
	{
		mId = id;
	}

	public ManagedGArc addManagedArc(ManagedGNode node1, ManagedGNode node2, boolean directed)
	{
		ManagedGArc arc = mEntityFactory.createArc();
		arc.setManagedNodes(node1, node2);

		// Combine graphs, if needed
		ManagedGGraph sourceGraph = node1.getManagedGraph();
		ManagedGGraph targetGraph = node2.getManagedGraph();
		if (sourceGraph != targetGraph)
		{
			if (sourceGraph.getNodes().size() > targetGraph.getNodes().size())
			{
				ManagedGGraph temp = targetGraph;
				targetGraph = sourceGraph;
				sourceGraph = temp;
			}

			for (ManagedGArc arcToMove : sourceGraph.getManagedArcs())
			{
				sourceGraph.removeManagedArc(arcToMove);
				targetGraph.addManagedArc(arcToMove);
			}

			for (ManagedGNode nodeToMove : sourceGraph.getManagedNodes())
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

	public void removeManagedArc(ManagedGArc arc)
	{
		if (!mArcs.contains(arc))
			throw new IllegalStateException("Attempt to remove Arc that is not part of this Model");

		Set<ManagedGNode> nodesToMove = new HashSet<ManagedGNode>();
		Queue<ManagedGNode> nodeSearchQueue = new LinkedList<ManagedGNode>();

		ManagedGNode node1 = arc.getStartManagedNode();
		ManagedGNode node2 = arc.getEndManagedNode();

		nodeSearchQueue.add(node2);
		boolean split = true;
		while (nodeSearchQueue.size() > 0 && split)
		{
			ManagedGNode currentNode = nodeSearchQueue.poll();
			nodesToMove.add(currentNode);
			for (ManagedGArc arcToNeighbor : currentNode.getManagedArcs())
			{
				if (arcToNeighbor != arc)
				{
					ManagedGNode neighbor = arcToNeighbor.getOtherManagedNode(currentNode);
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
			ManagedGGraph sourceGraph = node1.getManagedGraph();
			ManagedGGraph targetGraph = createManagedGraph();

			Set<ManagedGArc> arcsToMove = new HashSet<ManagedGArc>();
			for (ManagedGNode nodeToMove : nodesToMove)
			{
				arcsToMove.addAll(nodeToMove.getManagedArcs());
				sourceGraph.removeManagedNode(nodeToMove);
				targetGraph.addManagedNode(nodeToMove);
			}

			for (ManagedGArc arcToMove : arcsToMove)
			{
				sourceGraph.removeManagedArc(arcToMove);
				if (arcToMove != arc)
					targetGraph.addManagedArc(arcToMove);
			}

			fireGraphsSplit(sourceGraph, targetGraph);
		}
	}

	public void managedNodeAdded(ManagedGNode node)
	{
		mNodes.add(node);
	}

	public void managedNodeRemoved(ManagedGNode node)
	{
		mNodes.remove(node);
	}

	public void managedArcAdded(ManagedGArc arc)
	{
		mArcs.add(arc);
	}

	public void managedArcRemoved(ManagedGArc arc)
	{
		mArcs.remove(arc);
	}

	public void managedGraphRemoved(ManagedGGraph graph)
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

	private void fireGraphAdded(ManagedGGraph graph)
	{
		sLog.debug("Firing " + this + ".GraphAdded(graph:" + graph + ")");
		Set<GModelListener> listeners = new HashSet<GModelListener>(mListeners);
		for (GModelListener listener : listeners)
			listener.graphAdded(this, graph);
	}

	private void fireGraphRemoved(ManagedGGraph graph)
	{
		sLog.debug("Firing " + this + ".GraphRemoved(graph:" + graph + ")");
		Set<GModelListener> listeners = new HashSet<GModelListener>(mListeners);
		for (GModelListener listener : listeners)
			listener.graphRemoved(this, graph);
	}

	private void fireGraphsMerged(ManagedGGraph source, ManagedGGraph target)
	{
		sLog.debug("Firing " + this + ".GraphsMerged(source:" + source + ",target:" + target + ")");
		Set<GModelListener> listeners = new HashSet<GModelListener>(mListeners);
		for (GModelListener listener : listeners)
			listener.graphsMerged(this, source, target);
	}

	private void fireGraphsSplit(ManagedGGraph source, ManagedGGraph target)
	{
		sLog.debug("Firing " + this + ".GraphsSplit(source:" + source + ",target:" + target + ")");
		Set<GModelListener> listeners = new HashSet<GModelListener>(mListeners);
		for (GModelListener listener : listeners)
			listener.graphSplit(this, source, target);
	}

	private ManagedGGraph createManagedGraph()
	{
		ManagedGGraph graph = mEntityFactory.createGraph();

		graph.setManagedModel(this);

		mGraphs.add(graph);
		fireGraphAdded(graph);

		return graph;
	}
}
