package com.rjcass.hashiokakero;

public class Position
{
    public enum Alignment
    {
        MISALIGNED, COINCIDENT, COLUMN, ROW
    };

    private int mRow;
    private int mCol;

    public Position()
    {
    }

    public Position(int row, int col)
    {
        mRow = row;
        mCol = col;
    }

    public int getRow()
    {
        return mRow;
    }

    public int getCol()
    {
        return mCol;
    }

    public boolean alignedWith(Position p)
    {
        boolean result = false;
        if(mRow == p.mRow || mCol == p.mCol) result = true;
        return result;
    }

    public Alignment getAlignmentWith(Position p)
    {
        Alignment result = Alignment.MISALIGNED;
        if(mRow == p.mRow)
            if(mCol == p.mCol)
                result = Alignment.COINCIDENT;
            else
                result = Alignment.ROW;
        else
            result = Alignment.COLUMN;
        return result;
    }
}
