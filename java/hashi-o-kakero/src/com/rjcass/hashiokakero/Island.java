package com.rjcass.hashiokakero;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Island extends Occupant
{
	public enum CompassPoint
	{
		NORTH, EAST, SOUTH, WEST
	};

	private int mAllowedBridgeCount;
	private Map<CompassPoint, Set<Bridge>> mBridges;
	private int mTotalBridgeCount;
	private int mCommittedBridgeCount;
	private Cell mCell;

	public Island(Cell cell, int allowedBridgeCount)
	{
		mCell = cell;

		if (allowedBridgeCount < 1)
			throw new HashiException("Allowed bridges must be greater than 0");

		mAllowedBridgeCount = allowedBridgeCount;
		mBridges = new HashMap<CompassPoint, Set<Bridge>>();
		for (CompassPoint cp : CompassPoint.values())
			mBridges.put(cp, new HashSet<Bridge>());

		doCommit();
	}

	public int getAllowedBridgeCount()
	{
		return mAllowedBridgeCount;
	}

	/**
	 * Return the number of bridges still needed to be committed (allowed -
	 * committed).
	 * 
	 * @return the number of bridges still needed to be committed.
	 */
	public int getAvailableBridgeCount()
	{
		return mAllowedBridgeCount - mCommittedBridgeCount;
	}

	public int getUncommittedBridgeCount()
	{
		return mTotalBridgeCount - mCommittedBridgeCount;
	}

	public int getCommittedBridgeCount()
	{
		return mCommittedBridgeCount;
	}

	public boolean isCompleted()
	{
		return (getAvailableBridgeCount() == 0 ? true : false);
	}

	public Map<CompassPoint, Set<Bridge>> getUncommittedBridges()
	{
		Map<CompassPoint, Set<Bridge>> map = new HashMap<CompassPoint, Set<Bridge>>();
		for (CompassPoint cp : CompassPoint.values())
		{
			Set<Bridge> bridges = new HashSet<Bridge>();
			map.put(cp, bridges);
			for (Bridge bridge : mBridges.get(cp))
				if (!bridge.isCommitted())
					bridges.add(bridge);
		}
		return map;
	}

	public Map<CompassPoint, Integer> getConnectionCounts()
	{
		Map<CompassPoint, Integer> results = new HashMap<CompassPoint, Integer>();

		for (CompassPoint cp : CompassPoint.values())
			results.put(cp, mBridges.get(cp).size());

		return results;
	}

	public Cell getCell()
	{
		return mCell;
	}

	public Island getNeighbor(CompassPoint direction)
	{
		Island result = null;

		Cell cell = mCell;
		while (cell != null && result == null)
		{
			if (direction == CompassPoint.NORTH)
				cell = cell.getNorth();
			else if (direction == CompassPoint.SOUTH)
				cell = cell.getSouth();
			else if (direction == CompassPoint.EAST)
				cell = cell.getEast();
			else if (direction == CompassPoint.WEST)
				cell = cell.getWest();
			if (cell != null)
				result = cell.getIsland();
		}

		return result;
	}

	public void addBridge(CompassPoint dir, Bridge bridge)
	{
		Set<Bridge> bridges = mBridges.get(dir);
		if (bridges.size() > 1)
			throw new HashiException("Attempt to add third bridge in same direction");
		bridges.add(bridge);
		mTotalBridgeCount++;
	}

	public void bridgeCommitted(Bridge bridge)
	{
		mCommittedBridgeCount++;
		if (mCommittedBridgeCount == mAllowedBridgeCount)
		{
			for (CompassPoint cp : CompassPoint.values())
			{
				Set<Bridge> cpBridges = new HashSet<Bridge>(mBridges.get(cp));
				for (Bridge b : cpBridges)
				{
					if (!b.isCommitted())
						b.remove();
				}
			}
		}
	}

	public void removeBridge(Bridge bridge)
	{
		for (CompassPoint cp : CompassPoint.values())
		{
			if (mBridges.get(cp).remove(bridge))
				mTotalBridgeCount--;
		}
	}
}
