package com.rjcass.hashiokakero;

import java.util.HashSet;
import java.util.Set;

public class BridgeSegment extends AbstractGridOccupant
{
    private Set<Bridge> mBridges;
    private Bridge.Orientation mOrientation;

    public BridgeSegment(Grid grid, int x, int y, Bridge bridge)
    {
        super(grid, x, y);
        mBridges = new HashSet<Bridge>();

        mBridges.add(bridge);
        mOrientation = bridge.getOrientation();
    }

    public void addSegment(Bridge bridge) throws HashiException
    {
        if(mBridges.size() >= 2)
            throw new HashiException("Already have two segments in this cell");
        if(bridge.getOrientation() != mOrientation)
            throw new HashiException(
                    "Adding bridge segment with different orientation");

        mBridges.add(bridge);
    }

    public int getSegmentCount()
    {
        return mBridges.size();
    }

    public Bridge.Orientation getOrientation()
    {
        return mOrientation;
    }

    public boolean isObstacle()
    {
        return true;
    }
}
