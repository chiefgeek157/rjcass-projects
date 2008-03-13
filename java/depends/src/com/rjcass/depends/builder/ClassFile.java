package com.rjcass.depends.builder;

import com.rjcass.depends.ClassJ;

public class ClassFile implements Comparable<ClassFile>
{
    private String mName;
    private String mPackageName;
    private JarFile mJar;
    private boolean mInterface;

    public ClassFile(String name, JarFile jar)
    {
        mName = name;
        mPackageName = ClassJ.getPackageName(mName);
        mJar = jar;
    }

    public String getName()
    {
        return mName;
    }

    public String getPackageName()
    {
        return mPackageName;
    }

    public JarFile getJar()
    {
        return mJar;
    }

    public boolean isInterface()
    {
        return mInterface;
    }

    public void setInterface(boolean isInterface)
    {
        mInterface = isInterface;
    }

    public boolean equals(Object obj)
    {
        boolean result = false;
        if(obj != null && obj instanceof ClassFile
                && mName.equals(((ClassFile)obj).mName)) result = true;
        return result;
    }

    @Override
    public int compareTo(ClassFile o)
    {
        return mName.compareTo(o.mName);
    }

    public String toString()
    {
        return new StringBuilder().append("ClassFile[").append(mName).append(
                "]").toString();
    }
}
