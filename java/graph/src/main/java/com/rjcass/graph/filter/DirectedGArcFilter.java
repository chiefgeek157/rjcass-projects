package com.rjcass.graph.filter;

import com.rjcass.graph.GArc;
import com.rjcass.graph.GArcFilter;
import com.rjcass.graph.GNode;

public class DirectedGArcFilter implements GArcFilter
{
	private GNode mSource;
	private boolean mDirected;
	private GArc.Direction mDirection;

	/**
	 * Return only Arcs that are directed or undirected connected to the given
	 * source node.
	 * 
	 * @param directed
	 *            True if directed Arcs should be returned, false if only
	 *            undirected Arcs should be returned.
	 */
	public DirectedGArcFilter(boolean directed)
	{
		mDirected = directed;
	}

	/**
	 * Return only Arcs that are inbound or outbound with respect to the given
	 * Node. Implies that only directed Arcs will be included.
	 * 
	 * @param source
	 *            The source Node from which to consider the given Direction.
	 * @param direction
	 *            The direction to consider with respect to the given source
	 *            node.
	 * @throws IllegalArgumentException
	 *             if source is null or direction is UNDIRECTED.
	 */
	public DirectedGArcFilter(GNode source, GArc.Direction direction)
	{
		if (source == null)
			throw new IllegalArgumentException("Node cannot be null");
		if (direction == GArc.Direction.UNDIRECTED)
			throw new IllegalArgumentException("Direction cannot be UNDIRECTED");
		mSource = source;
		mDirected = true;
		mDirection = direction;
	}

	public boolean passes(GArc arc)
	{
		boolean result = false;
		if (mDirected)
		{
			if (arc.isDirected())
			{
				if (mSource == null)
					result = true;
				else
					result = (arc.getDirection(mSource) == mDirection);
			}
		}
		else
		{
			result = (!arc.isDirected());
		}
		return result;
	}
}
