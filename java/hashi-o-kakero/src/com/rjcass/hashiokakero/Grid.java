package com.rjcass.hashiokakero;

public interface Grid
{
    void setOccupant(int row, int col, Occupant occupant);

    Occupant getOccupant(int row, int col);
}
