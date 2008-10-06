package com.rjcass.hashiokakero;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.rjcass.hashiokakero.Bridge.Orientation;

public class Cell
{
	private Position mPosition;
	private Island mIsland;
	private Bridge mNSBridge1, mNSBridge2;
	private Bridge mEWBridge1, mEWBridge2;
	private Cell mEast, mWest, mNorth, mSouth;

	public Cell(int row, int col)
	{
		mPosition = new Position(row, col);
	}

	public void setEast(Cell cell)
	{
		mEast = cell;
	}

	public Cell getEast()
	{
		return mEast;
	}

	public void setWest(Cell cell)
	{
		mWest = cell;
	}

	public Cell getWest()
	{
		return mWest;
	}

	public void setNorth(Cell cell)
	{
		mNorth = cell;
	}

	public Cell getNorth()
	{
		return mNorth;
	}

	public void setSouth(Cell cell)
	{
		mSouth = cell;
	}

	public Cell getSouth()
	{
		return mSouth;
	}

	public Position getPosition()
	{
		return mPosition;
	}

	public boolean isCommitted()
	{
		return (mIsland != null || mNSBridge1 != null && mNSBridge1.isCommitted() || mNSBridge2 != null && mNSBridge2.isCommitted()
				|| mEWBridge1 != null && mEWBridge1.isCommitted() || mEWBridge2 != null && mEWBridge2.isCommitted());
	}

	public void setIsland(Island island)
	{
		if (mIsland != null || mNSBridge1 != null || mNSBridge2 != null || mEWBridge1 != null || mEWBridge2 != null)
			throw new IllegalStateException("Island or bridge already set");
		mIsland = island;
	}

	public Island getIsland()
	{
		return mIsland;
	}

	public void setNSBridge(Bridge bridge)
	{
		if (mIsland != null || mNSBridge1 != null && mNSBridge2 != null)
			throw new IllegalStateException("Island or both NS bridges already set");
		if (mNSBridge1 == null)
			mNSBridge1 = bridge;
		else
			mNSBridge2 = bridge;
	}

	public void setEWBridge(Bridge bridge)
	{
		if (mIsland != null || mEWBridge1 != null && mEWBridge2 != null)
			throw new IllegalStateException("Island or both EW bridges already set");
		if (mEWBridge1 == null)
			mEWBridge1 = bridge;
		else
			mEWBridge2 = bridge;
	}

	public void commit(Bridge bridge)
	{
		if (bridge.getOrientation() == Orientation.NORTH_SOUTH)
		{
			if (mEWBridge1 != null && bridge != mEWBridge1)
			{
				mEWBridge1.remove();
			}
			if (mEWBridge2 != null && bridge != mEWBridge2)
			{
				mEWBridge2.remove();
			}
		}
		else
		{
			if (mNSBridge1 != null && bridge != mNSBridge1)
			{
				mNSBridge1.remove();
			}
			if (mNSBridge2 != null && bridge != mNSBridge2)
			{
				mNSBridge2.remove();
			}
		}
	}

	public void remove(Bridge bridge)
	{
		if (bridge == mNSBridge1)
			mNSBridge1 = null;
		else if (bridge == mNSBridge2)
			mNSBridge2 = null;
		else if (bridge == mEWBridge1)
			mEWBridge1 = null;
		else if (bridge == mEWBridge2)
			mEWBridge2 = null;
	}

	public String toString(int line)
	{
		StringWriter sw = new StringWriter();
		PrintWriter w = new PrintWriter(sw);
		switch (line)
		{
			case 0:
				if (mIsland != null)
				{
					if (mIsland.isCompleted())
						w.print("/==\\");
					else
						w.print("+--+");
				}
				else
				{
					w.print(" ");
					if (mNSBridge1 != null)
						if (mNSBridge1.isCommitted())
							w.print("#");
						else
							w.print("|");
					else
						w.print(" ");
					if (mNSBridge2 != null)
						if (mNSBridge2.isCommitted())
							w.print("#");
						else
							w.print("|");
					else
						w.print(" ");
					w.print(" ");
				}
				break;
			case 1:
				if (mIsland != null)
				{
					w.print("|  |");
				}
				else
				{
					if (mEWBridge1 != null)
						if (mEWBridge1.isCommitted())
							w.print("#");
						else
							w.print("-");
					else
						w.print(" ");
					if (mNSBridge1 != null)
					{
						if (mNSBridge1.isCommitted())
							w.print("#");
						else if (mEWBridge1 != null)
							if (mEWBridge1.isCommitted())
								w.print("#");
							else
								w.print("+");
						else
							w.print("|");
					}
					else
					{
						if (mEWBridge1 != null)
							if (mEWBridge1.isCommitted())
								w.print("#");
							else
								w.print("-");
						else
							w.print(" ");
					}
					if (mNSBridge2 != null)
					{
						if (mNSBridge2.isCommitted())
							w.print("#");
						else if (mEWBridge1 != null)
							if (mEWBridge1.isCommitted())
								w.print("#");
							else
								w.print("+");
						else
							w.print("|");
					}
					else
					{
						if (mEWBridge1 != null)
							if (mEWBridge1.isCommitted())
								w.print("#");
							else
								w.print("-");
						else
							w.print(" ");
					}
					if (mEWBridge1 != null)
						if (mEWBridge1.isCommitted())
							w.print("#");
						else
							w.print("-");
					else
						w.print(" ");
				}
				break;
			case 2:
				if (mIsland != null)
				{
					w.printf("|%2d|", mIsland.getAllowedBridgeCount());
				}
				else
				{
					if (mEWBridge2 != null)
						if (mEWBridge2.isCommitted())
							w.print("#");
						else
							w.print("-");
					else
						w.print(" ");
					if (mNSBridge1 != null)
					{
						if (mNSBridge1.isCommitted())
							w.print("#");
						else if (mEWBridge2 != null)
							if (mEWBridge2.isCommitted())
								w.print("#");
							else
								w.print("+");
						else
							w.print("|");
					}
					else
					{
						if (mEWBridge2 != null)
							if (mEWBridge2.isCommitted())
								w.print("#");
							else
								w.print("-");
						else
							w.print(" ");
					}
					if (mNSBridge2 != null)
					{
						if (mNSBridge2.isCommitted())
							w.print("#");
						else if (mEWBridge2 != null)
							if (mEWBridge2.isCommitted())
								w.print("#");
							else
								w.print("+");
						else
							w.print("|");
					}
					else
					{
						if (mEWBridge2 != null)
							if (mEWBridge2.isCommitted())
								w.print("#");
							else
								w.print("-");
						else
							w.print(" ");
					}
					if (mEWBridge2 != null)
						if (mEWBridge2.isCommitted())
							w.print("#");
						else
							w.print("-");
					else
						w.print(" ");
				}
				break;
			case 3:
				if (mIsland != null)
				{
					if (mIsland.isCompleted())
						w.print("\\==/");
					else
						w.print("+--+");
				}
				else
				{
					w.print(" ");
					if (mNSBridge1 != null)
						if (mNSBridge1.isCommitted())
							w.print("#");
						else
							w.print("|");
					else
						w.print(" ");
					if (mNSBridge2 != null)
						if (mNSBridge2.isCommitted())
							w.print("#");
						else
							w.print("|");
					else
						w.print(" ");
					w.print(" ");
				}
				break;
		}
		return sw.toString();
	}
}
