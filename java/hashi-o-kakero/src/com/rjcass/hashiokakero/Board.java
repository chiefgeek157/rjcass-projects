package com.rjcass.hashiokakero;

import java.io.PrintWriter;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

public class Board implements Grid
{
    private int mRows;
    private int mCols;
    private Occupant[][] mOccupants;

    public Board(int rows, int cols)
    {
        if(rows < 1 || cols < 1)
            throw new HashiException("Rows and columns must be greater than 0");

        mRows = rows;
        mCols = cols;

        mOccupants = new Occupant[rows][cols];
    }

    public void addIsland(int row, int col, int maxBridges)
    {
        Island island = new Island(this, row, col, maxBridges);
        setOccupant(row, col, island);
    }

    public void setOccupant(int row, int col, Occupant occupant)
    {
        if(occupant == null)
            throw new IllegalArgumentException("Occupant cannot be null");

        mOccupants[row][col] = occupant;
    }

    public Occupant getOccupant(int row, int col)
    {
        return mOccupants[row][col];
    }

    public void solve()
    {
        Deque<Island> remainingIslands = new LinkedList<Island>();

        createPossibleBridges();
        while(remainingIslands.size() > 0)
        {
            Island island = remainingIslands.removeFirst();
            checkIsland(island);
        }
    }

    public void dump(PrintWriter w)
    {
        w.print("     ");
        for(int col = 0; col < mCols; col++)
            w.printf("%-2d ", col);
        w.println();
        for(int row = 0; row < mRows; row++)
        {
            for(int subrow = 0; subrow < 3; subrow++)
            {
                switch(subrow)
                {
                case 0:
                case 2:
                    w.print("  : ");
                    break;
                case 1:
                    w.printf("%2d: ", row);
                }
                for(int col = 0; col < mCols; col++)
                {
                    Occupant occupant = mOccupants[row][col];
                    if(occupant != null)
                    {
                        if(occupant instanceof Island)
                        {
                            dump((Island)occupant, subrow, w);
                        }
                        else if(occupant instanceof BridgeSegment)
                        {
                            dump((BridgeSegment)occupant, subrow, w);
                        }
                    }
                    else
                    {
                        w.print("   ");
                    }
                }
                w.println();
            }
        }
        w.flush();
    }

    private void createPossibleBridges()
    {
        // Search only North and West
        for(int row = 0; row < mRows; row++)
        {
            for(int col = 0; col < mCols; col++)
            {
                Occupant occupant = mOccupants[row][col];
                if(occupant != null && !occupant.isObstacle())
                {
                    if(row > 0)
                    {
                        for(int checkRow = row - 1; checkRow >= 0; checkRow--)
                        {
                            Occupant checkOccupant = mOccupants[checkRow][col];
                            if(checkOccupant == null || checkOccupant != null && !checkOccupant.isObstacle())
                            {

                            }
                        }
                    }
                    if(col > 0)
                    {

                    }
                }
            }
        }
    }

    private void checkIsland(Island island)
    {
        if(!island.isCompleted())
        {

        }
    }

    private void dump(Island island, int row, PrintWriter w)
    {
        Map<Island.CompassPoint,Integer> bridgeCount = island
                .getConnectionCount();
        switch(row)
        {
        case 0:
            if(island.isCompleted())
                w.print("\\");
            else
                w.print("+");
            if(bridgeCount.get(Island.CompassPoint.NORTH) == 0)
                w.print("-");
            else if(bridgeCount.get(Island.CompassPoint.NORTH) == 1)
                w.print("|");
            else
                w.print("H");
            if(island.isCompleted())
                w.print("/");
            else
                w.print("+");
            break;
        case 1:
            if(bridgeCount.get(Island.CompassPoint.WEST) == 0)
                w.print("|");
            else if(bridgeCount.get(Island.CompassPoint.WEST) == 1)
                w.print("-");
            else
                w.print("=");
            w.print(island.getMaxBridges());
            if(bridgeCount.get(Island.CompassPoint.EAST) == 0)
                w.print("|");
            else if(bridgeCount.get(Island.CompassPoint.EAST) == 1)
                w.print("-");
            else
                w.print("=");
            break;
        case 2:
            if(island.isCompleted())
                w.print("/");
            else
                w.print("+");
            if(bridgeCount.get(Island.CompassPoint.SOUTH) == 0)
                w.print("-");
            else if(bridgeCount.get(Island.CompassPoint.SOUTH) == 1)
                w.print("|");
            else
                w.print("H");
            if(island.isCompleted())
                w.print("\\");
            else
                w.print("+");
            break;
        }
    }

    private void dump(BridgeSegment s, int row, PrintWriter w)
    {
        switch(s.getOrientation())
        {
        case EAST_WEST:
            if(row == 1)
            {
                switch(s.getSegmentCount())
                {
                case 1:
                    w.print("---");
                    break;
                case 2:
                    w.print("===");
                    break;
                }
            }
            else
            {
                w.print("   ");
            }
            break;
        case NORTH_SOUTH:
            switch(s.getSegmentCount())
            {
            case 1:
                w.print(" | ");
                break;
            case 2:
                w.print(" H ");
                break;
            }
            break;
        }
    }
}
