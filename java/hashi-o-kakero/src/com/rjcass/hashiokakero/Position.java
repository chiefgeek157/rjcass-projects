package com.rjcass.hashiokakero;

public class Position
{
    public enum Alignment
    {
        MISALIGNED, COINCIDENT, VERTICAL, HORIZONTAL
    };

    private int mX;
    private int mY;

    public Position()
    {
    }

    public Position(int x, int y)
    {
        mX = x;
        mY = y;
    }

    public int getX()
    {
        return mX;
    }

    public int getY()
    {
        return mY;
    }

    public boolean alignedWith(Position p)
    {
        boolean result = false;
        if(mX == p.mX || mY == p.mY) result = true;
        return result;
    }

    public Alignment getAlignmentWith(Position p)
    {
        Alignment result = Alignment.MISALIGNED;
        if(mX == p.mX)
            if(mY == p.mY)
                result = Alignment.COINCIDENT;
            else
                result = Alignment.VERTICAL;
        else
            result = Alignment.HORIZONTAL;
        return result;
    }
}
