package com.rjcass.depends;

import java.util.Set;

public class Package extends Entity
{
    public Package(String name)
    {
        setName(name);
    }

    public Jar getJar()
    {
        return getParent(Jar.class);
    }

    public void setJar(Jar jar)
    {
        setParent(jar);
    }

    public void add(ClassJ cls)
    {
        addChild(cls);
    }

    public Set<ClassJ> getClasses()
    {
        return getChildren(ClassJ.class);
    }

    public String toString()
    {
        return "Package[" + getName() + "]";
    }
}
