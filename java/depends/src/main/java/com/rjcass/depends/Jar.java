package com.rjcass.depends;

import java.util.Set;

public class Jar extends Entity
{
    public Jar(String name)
    {
        setName(name);
    }

    public void addPackage(Package pkg)
    {
        addChild(pkg);
    }

    public Set<Package> getPackages()
    {
        return getChildren(Package.class);
    }

    public String toString()
    {
        return "Jar[" + getName() + "]";
    }
}
