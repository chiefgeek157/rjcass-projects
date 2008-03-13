package com.rjcass.hashiokakero;

import com.rjcass.hashiokakero.Position.Alignment;

public class Bridge
{
    public enum Orientation
    {
        NORTH_SOUTH, EAST_WEST
    };

    private Island mStart;
    private Island mEnd;
    private boolean mCommitted;
    private Orientation mOrientation;

    public Bridge(Island start, Island end)
    {
        mStart = start;
        mEnd = end;

        Position startPos = start.getPosition();
        Position endPos = end.getPosition();

        Alignment alignment = startPos.getAlignmentWith(endPos);
        if(alignment == Alignment.MISALIGNED
                || alignment == Alignment.COINCIDENT)
            throw new HashiException(
                    "Islands must be aligned and non-coincident");

        Grid grid = start.getGrid();
        if(alignment == Alignment.HORIZONTAL)
        {
            mOrientation = Orientation.EAST_WEST;
            int row = startPos.getY();
            int startCol = startPos.getX();
            int endCol = endPos.getX();
            for(int col = startCol + 1; col < endCol; col++)
            {
                addSegment(grid, row, col);
            }
        }
        else
        {
            mOrientation = Orientation.NORTH_SOUTH;
            int startRow = startPos.getY();
            int endRow = endPos.getY();
            int col = startPos.getX();
            for(int row = startRow + 1; row < endRow; row++)
            {
                addSegment(grid, row, col);
            }
        }
    }

    public Orientation getOrientation()
    {
        return mOrientation;
    }

    private void addSegment(Grid grid, int row, int col)
    {
        Occupant occupant = grid.getOccupant(row, col);
        if(occupant != null)
        {
            if(occupant instanceof BridgeSegment)
            {
                BridgeSegment segment = (BridgeSegment)occupant;
                if(segment.getOrientation() != mOrientation)
                    throw new HashiException(
                            "Existing segment has different orientation");
                else if(segment.getSegmentCount() != 1)
                    throw new HashiException(
                            "Existing segment already has two bridges");
                else
                {
                    segment.addSegment(this);
                }
            }
            else
            {
                throw new HashiException(
                        "Cannot add bridge segment due to occupant: "
                                + occupant);
            }
        }
        else
        {
            BridgeSegment segment = new BridgeSegment(grid, row, col, this);
            grid.setOccupant(row, col, segment);
        }
    }
}
