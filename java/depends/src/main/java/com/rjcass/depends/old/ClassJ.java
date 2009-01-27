package com.rjcass.depends.old;

public class ClassJ extends Entity
{
    private boolean mInterface;

    public static String getPackageName(String className)
    {
        String packageName = null;
        int index = className.lastIndexOf('.');
        if(index >= 0)
            packageName = className.substring(0, index);
        else
            packageName = "<default>";
        return packageName;
    }

    public ClassJ(String name)
    {
        setName(name);
    }

    public Package getPackage()
    {
        return getParent(Package.class);
    }

    public void setPackage(Package pkg)
    {
        setParent(pkg);
    }

    public String getPackageName()
    {
        String name = null;
        Package pkg = getPackage();
        if(pkg == null)
        {
            name = getPackageName(getName());
        }
        else
        {
            name = pkg.getName();
        }
        return name;
    }

    public boolean isInterface()
    {
        return mInterface;
    }

    public void setInterface(boolean isInterface)
    {
        mInterface = isInterface;
    }

    public String toString()
    {
        StringBuilder s = new StringBuilder();
        if(mInterface)
            s.append("Interface");
        else
            s.append("Class");
        s.append("[").append(getName()).append("]");
        return s.toString();
    }
}
