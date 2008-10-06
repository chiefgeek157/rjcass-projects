package com.rjcass.hashiokakero;

import java.util.LinkedList;

import com.rjcass.hashiokakero.Position.Alignment;

public class Bridge extends Occupant
{
	public enum Orientation
	{
		NORTH_SOUTH, EAST_WEST
	};

	private Island mStart;
	private Island mEnd;
	private Orientation mOrientation;
	private LinkedList<Cell> mCells;

	public Bridge(Island island1, Island island2)
	{
		mCells = new LinkedList<Cell>();

		Position pos1 = island1.getCell().getPosition();
		Position pos2 = island2.getCell().getPosition();

		Alignment alignment = pos1.getAlignmentWith(pos2);
		if (alignment == Alignment.MISALIGNED || alignment == Alignment.COINCIDENT)
			throw new HashiException("Islands must be aligned and non-coincident");

		if (alignment == Alignment.ROW)
		{
			mOrientation = Orientation.EAST_WEST;

			int startCol, endCol;
			if (pos1.getCol() < pos2.getCol())
			{
				mStart = island1;
				mEnd = island2;
				startCol = pos1.getCol();
				endCol = pos2.getCol();
			}
			else
			{
				mStart = island2;
				mEnd = island1;
				startCol = pos2.getCol();
				endCol = pos1.getCol();
			}

			Cell cell = mStart.getCell().getEast();
			for (int col = startCol + 1; col < endCol; col++)
			{
				mCells.add(cell);
				cell.setEWBridge(this);
				cell = cell.getEast();
			}
		}
		else
		{
			mOrientation = Orientation.NORTH_SOUTH;

			int startRow, endRow;
			if (pos1.getRow() < pos2.getRow())
			{
				mStart = island1;
				mEnd = island2;
				startRow = pos1.getRow();
				endRow = pos2.getRow();
			}
			else
			{
				mStart = island2;
				mEnd = island1;
				startRow = pos2.getRow();
				endRow = pos1.getRow();
			}

			Cell cell = mStart.getCell().getSouth();
			for (int row = startRow + 1; row < endRow; row++)
			{
				mCells.add(cell);
				cell.setNSBridge(this);
				cell = cell.getSouth();
			}
		}
	}

	public Orientation getOrientation()
	{
		return mOrientation;
	}

	public void commit()
	{
		doCommit();
		for (Cell cell : mCells)
		{
			cell.commit(this);
		}
		mStart.bridgeCommitted(this);
		mEnd.bridgeCommitted(this);
	}

	public void remove()
	{
		for (Cell cell : mCells)
		{
			cell.remove(this);
		}
		mStart.removeBridge(this);
		mEnd.removeBridge(this);
	}

	public String toString()
	{
		return "B((" + mStart.getCell().getPosition().getRow() + "," + mStart.getCell().getPosition().getCol() + ")->("
				+ mEnd.getCell().getPosition().getRow() + "," + mEnd.getCell().getPosition().getCol() + "))";
	}
}
