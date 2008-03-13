package com.rjcass.hashiokakero;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Island extends AbstractGridOccupant
{
    public enum CompassPoint
    {
        NORTH, EAST, SOUTH, WEST
    };

    private int mMaxBridges;
    private Map<CompassPoint,Set<Bridge>> mBridges;

    private int mCurrentBridges;

    public Island(Grid grid, int x, int y, int maxBridges)
    {
        super(grid, x, y);
        
        if(maxBridges < 1)
            throw new HashiException("Maximum bridges must be greater than 0");

        mMaxBridges = maxBridges;
        mBridges = new HashMap<CompassPoint,Set<Bridge>>();
        for(CompassPoint cp : CompassPoint.values())
            mBridges.put(cp, new HashSet<Bridge>());

    }

    public int getMaxBridges()
    {
        return mMaxBridges;
    }

    public boolean isObstacle()
    {
        return false;
    }

    public boolean isCompleted()
    {
        return(mCurrentBridges < mMaxBridges ? false : true);
    }

    public Map<CompassPoint,Integer> getConnectionCount()
    {
        Map<CompassPoint,Integer> results = new HashMap<CompassPoint,Integer>();

        for(CompassPoint cp : CompassPoint.values())
            results.put(cp, mBridges.get(cp).size());

        return results;
    }
}
