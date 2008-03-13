package com.rjcass.hashiokakero;

public abstract class AbstractGridOccupant implements GridOccupant
{
    private Grid mGrid;
    private Position mPosition;

    public AbstractGridOccupant(Grid grid,int x, int y)
    {
        mGrid = grid;
        mPosition = new Position(x,y);
    }

    @Override
    public Grid getGrid()
    {
        return mGrid;
    }

    public Position getPosition()
    {
        return mPosition;
    }
}
