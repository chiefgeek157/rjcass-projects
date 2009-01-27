package com.rjcass.depends.old;

import java.util.HashMap;
import java.util.Map;

public class Repository
{
    private Map<String,Jar> mJars;

    public Repository()
    {
        mJars = new HashMap<String,Jar>();
    }

    Jar getJar(String name)
    {
        return mJars.get(name);
    }
}
