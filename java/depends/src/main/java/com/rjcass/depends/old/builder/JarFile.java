package com.rjcass.depends.old.builder;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;

import com.rjcass.util.IterableEnumeration;

public class JarFile
{
    //private static Log sLog = LogFactory.getLog(JarFile.class);

    private String mName;
    private String mPath;

    public static String getJarName(String path)
    {
        if(path == null)
            throw new IllegalArgumentException("Path cannot be null");

        File file = new File(path);
        if(!file.canRead())
        {
            throw new BuilderException("Cannot read " + path);
        }
        return file.getName();
    }

    public JarFile(String path)
    {
        if(path == null)
            throw new IllegalArgumentException("Path cannot be null");

        mPath = path;
        mName = getJarName(path);
    }

    public String getName()
    {
        return mName;
    }

    public Set<ClassFile> getClassFiles()
    {
        Set<ClassFile> results = new HashSet<ClassFile>();

        java.util.jar.JarFile jarFile;
        try
        {
            jarFile = new java.util.jar.JarFile(mPath);
        }
        catch(IOException e)
        {
            throw new BuilderException("Could not load jar file: " + mPath);
        }

        for(JarEntry entry : new IterableEnumeration<JarEntry>(jarFile
                .entries()))
        {
            String name = entry.getName();
            if(!entry.isDirectory() && name.endsWith(".class"))
            {
                String className = name.substring(0, name.indexOf('.'))
                        .replace('/', '.');
                ClassFile classFile = new ClassFile(className, this);
                results.add(classFile);
            }
        }
        return results;
    }

    public boolean equals(Object obj)
    {
        boolean result = false;
        if(obj != null && obj instanceof JarFile
                && mName.equals(((JarFile)obj).mName)) result = true;
        return result;
    }

    public String toString()
    {
        return new StringBuilder("JarFile[").append(getName()).append("]")
                .toString();
    }
}