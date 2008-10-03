package com.rjcass.hashiokakero;

import java.io.PrintWriter;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.hashiokakero.Island.CompassPoint;

public class Board
{
	private static Log sLog = LogFactory.getLog(Board.class);

	private int mRows;
	private int mCols;
	private Cell[][] mCells;

	public Board(int rows, int cols)
	{
		if (rows < 1 || cols < 1)
			throw new HashiException("Rows and columns must be greater than 0");

		mRows = rows;
		mCols = cols;

		mCells = new Cell[rows][cols];
		for (int i = 0; i < mRows; i++)
		{
			for (int j = 0; j < mCols; j++)
			{
				mCells[i][j] = new Cell(i, j);
				if (i > 0)
				{
					mCells[i][j].setNorth(mCells[i - 1][j]);
					mCells[i - 1][j].setSouth(mCells[i][j]);
				}
				if (j > 0)
				{
					mCells[i][j].setWest(mCells[i][j - 1]);
					mCells[i][j - 1].setEast(mCells[i][j]);
				}
			}
		}
	}

	public void addIsland(int row, int col, int maxBridges)
	{
		Cell cell = mCells[row][col];
		Island island = new Island(cell, maxBridges);
		cell.setIsland(island);
	}

	public void solve()
	{
		Deque<Island> remainingIslands = new LinkedList<Island>();

		for (int row = 0; row < mRows; row++)
		{
			for (int col = 0; col < mCols; col++)
			{
				Cell cell = mCells[row][col];
				Island island = cell.getIsland();
				if (island != null)
				{
					int allowed = island.getAllowedBridgeCount();
					remainingIslands.add(island);
					Island northNeighbor = island.getNeighbor(CompassPoint.NORTH);
					Island westNeighbor = island.getNeighbor(CompassPoint.WEST);
					if (northNeighbor != null)
					{
						int numToBuild = 2;
						int northAllowed = northNeighbor.getAllowedBridgeCount();
						if (allowed == 1)
						{
							if (northAllowed == 1)
								numToBuild = 0;
							else
								numToBuild = 1;
						}
						else if (allowed == 2)
						{
							if (northAllowed == 2)
								numToBuild = 1;
						}
						for (int i = 0; i < numToBuild; i++)
						{
							Bridge bridge = new Bridge(northNeighbor, island);
							northNeighbor.addBridge(CompassPoint.SOUTH, bridge);
							island.addBridge(CompassPoint.NORTH, bridge);
						}
					}
					if (westNeighbor != null)
					{
						int numToBuild = 2;
						int westAllowed = westNeighbor.getAllowedBridgeCount();
						if (allowed == 1)
						{
							if (westAllowed == 1)
								numToBuild = 0;
							else
								numToBuild = 1;
						}
						else if (allowed == 2)
						{
							if (westAllowed == 2)
								numToBuild = 1;
						}
						for (int i = 0; i < numToBuild; i++)
						{
							Bridge bridge = new Bridge(westNeighbor, island);
							westNeighbor.addBridge(CompassPoint.EAST, bridge);
							island.addBridge(CompassPoint.WEST, bridge);
						}
					}
				}
			}
		}

		Deque<Island> islandsToRework = new LinkedList<Island>();
		Deque<Island> swap;

		boolean progressMade = true;
		while (progressMade)
		{
			progressMade = false;
			while (remainingIslands.size() > 0)
			{
				Island island = remainingIslands.removeFirst();
				if (applyRules(island))
					progressMade = true;
				if (!island.isCompleted())
					islandsToRework.add(island);
			}
			swap = remainingIslands;
			remainingIslands = islandsToRework;
			islandsToRework = swap;
		}
		if (!progressMade)
			sLog.debug("Exiting due to no progress made");
	}

	public void dump(PrintWriter w)
	{
		w.print("      ");
		for (int col = 0; col < mCols; col++)
			w.printf(" %-2d ", col);
		w.println();
		for (int row = 0; row < mRows; row++)
		{
			for (int subrow = 0; subrow < 4; subrow++)
			{
				switch (subrow)
				{
					case 0:
					case 1:
					case 3:
						w.print("   : ");
						break;
					case 2:
						w.printf(" %2d: ", row);
				}
				for (int col = 0; col < mCols; col++)
				{
					Cell cell = mCells[row][col];
					w.print(cell.toString(subrow));
				}
				w.println();
			}
		}
		w.flush();
	}

	private boolean applyRules(Island island)
	{
		boolean progressMade = false;
		if (applyUncommittedEqualsAvailable(island))
			progressMade = true;
		if (applyConnectToEachNeighbor(island))
			progressMade = true;
		return progressMade;
	}

	private boolean applyUncommittedEqualsAvailable(Island island)
	{
		boolean progressMade = false;
		if (!island.isCompleted())
		{
			int available = island.getAvailableBridgeCount();
			int uncommitted = island.getUncommittedBridgeCount();
			if (available == uncommitted)
			{
				sLog.debug("Applying UncommittedEqualsAvailable to " + island);
				progressMade = true;
				Map<CompassPoint, Set<Bridge>> bridges = island.getUncommittedBridges();
				for (CompassPoint cp : CompassPoint.values())
				{
					for (Bridge bridge : bridges.get(cp))
						bridge.commit();
				}
			}
		}
		return progressMade;
	}

	private boolean applyConnectToEachNeighbor(Island island)
	{
		boolean progressMade = false;
		if (!island.isCompleted())
		{
			int available = island.getAvailableBridgeCount();
			int uncommitted = island.getUncommittedBridgeCount();
			if (uncommitted - available == 1)
			{
				sLog.debug("Applying ConnectToEachNeighbor to " + island);
				progressMade = true;
				Map<CompassPoint, Set<Bridge>> bridges = island.getUncommittedBridges();
				for (CompassPoint cp : CompassPoint.values())
				{
					for (Bridge bridge : bridges.get(cp))
					{
						bridge.commit();
						break;
					}
				}
			}
		}
		return progressMade;
	}
}
