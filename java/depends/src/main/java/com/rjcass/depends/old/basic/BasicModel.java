package com.rjcass.depends.old.basic;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.depends.old.ClassJ;
import com.rjcass.depends.old.Entity;
import com.rjcass.depends.old.Jar;
import com.rjcass.depends.old.Package;
import com.rjcass.depends.old.Relationship;
import com.rjcass.depends.old.builder.BuildableModel;

public class BasicModel implements BuildableModel
{
    public static BasicModel newInstance()
    {
        return new BasicModel();
    }

    private static Log sLog = LogFactory.getLog(BasicModel.class);

    private Map<String,Jar> mJars;
    private Map<String,Package> mPackages;
    private Map<String,ClassJ> mClasses;
    private Map<Entity,Set<Relationship>> mRelationships;

    public BasicModel()
    {
        mClasses = new HashMap<String,ClassJ>();
        mPackages = new HashMap<String,Package>();
        mJars = new HashMap<String,Jar>();
        mRelationships = new HashMap<Entity,Set<Relationship>>();
    }

    public Jar findJar(String name)
    {
        Jar jarInfo = mJars.get(name);
        if(jarInfo == null)
        {
            jarInfo = new Jar(name);
            mJars.put(name, jarInfo);
        }
        return jarInfo;
    }

    public Package findPackage(String name)
    {
        Package pkgInfo = mPackages.get(name);
        if(pkgInfo == null)
        {
            pkgInfo = new Package(name);
            mPackages.put(name, pkgInfo);
        }
        return pkgInfo;
    }

    public ClassJ findClass(String name)
    {
        ClassJ cls = mClasses.get(name);
        if(cls == null)
        {
            cls = new ClassJ(name);
            mClasses.put(name, cls);
            findPackage(cls.getPackageName());
        }
        return cls;
    }

    public void addRelationship(Relationship.Type type, ClassJ source,
            ClassJ target)
    {
        addEntityRelationship(type, source, target);
    }

    public void generalizeRelationships()
    {
        Set<Relationship> rels = new HashSet<Relationship>();
        for(Set<Relationship> sourceRels : mRelationships.values())
        {
            rels.addAll(sourceRels);
        }

        for(Relationship rel : rels)
        {
            generalizeRelationship(rel);
        }
    }

    public void dump(OutputStream os)
    {
        PrintWriter w = new PrintWriter(new OutputStreamWriter(os));
        w.println("ModelBuilder");
        w.println("Jars");
        for(Jar jar : mJars.values())
        {
            dump(w, "   ", jar);
        }
        w.println("Packages");
        for(Package pkg : mPackages.values())
        {
            dump(w, "   ", pkg);
        }
        w.println("Classes");
        for(ClassJ cls : mClasses.values())
        {
            dump(w, "   ", cls);
        }
        w.flush();
    }

    private void dump(PrintWriter w, String leader, Entity ent)
    {
        w.println(leader + ent);
        w.println(leader + leader + "Parent: " + ent.getParent());
        Set<Relationship> rels = ent.getSourceRelationships();
        if(!rels.isEmpty())
        {
            w.println(leader + "   Source Relationships");
            for(Relationship rel : rels)
            {
                dump(w, leader + "      ", rel);
            }
        }
        rels = ent.getTargetRelationships();
        if(!rels.isEmpty())
        {
            w.println(leader + "   Target Relationships");
            for(Relationship rel : rels)
            {
                dump(w, leader + "      ", rel);
            }
        }
    }

    private void dump(PrintWriter w, String leader, Relationship rel)
    {
        w.println(leader + rel);
        Set<Relationship> rels = rel.getCauses();
        if(!rels.isEmpty())
        {
            w.println(leader + "   Causes:");
            for(Relationship cause : rels)
                w.println(leader + "      " + cause);
        }
        rels = rel.getGeneralizations();
        if(!rels.isEmpty())
        {
            w.println(leader + "   Generalizations");
            for(Relationship general : rels)
            {
                w.println(leader + "      " + general);
            }
        }
    }

    private Relationship addEntityRelationship(Relationship.Type type,
            Entity source, Entity target)
    {
        Relationship rel = null;

        Set<Relationship> rels = mRelationships.get(source);
        if(rels == null)
        {
            // No relationships for this source
            rels = new HashSet<Relationship>();
            mRelationships.put(source, rels);
        }

        if(rel == null)
        {
            rel = new Relationship(type, source, target);
            if(rels.add(rel))
            {
                sLog.debug("Added new relationship " + rel);
                source.addSourceRelationship(rel);
                target.addTargetRelationship(rel);
            }
        }
        return rel;
    }

    private void generalizeRelationship(Relationship rel)
    {
        Entity sourceParent = rel.getSource().getParent();
        Entity targetParent = rel.getTarget().getParent();
        if(sourceParent != null && targetParent != null
                && !sourceParent.equals(targetParent))
        {
            Relationship parentRel = addEntityRelationship(
                    Relationship.Type.DEPENDS_ON, sourceParent, targetParent);
            generalizeRelationship(parentRel);
        }
    }
}
