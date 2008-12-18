package com.rjcass.depends;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class Entity implements Comparable<Entity>
{
    private static Log sLog = LogFactory.getLog(Entity.class);

    private String mName;
    private Set<Relationship> mSourceRelationships;
    private Set<Relationship> mTargetRelationships;
    private Entity mParent;
    private Set<Entity> mChildren;

    public Entity getParent()
    {
        return mParent;
    }

    public <T extends Entity> T getParent(Class<T> type)
    {
        return type.cast(mParent);
    }

    public Set<Entity> getChildren()
    {
        return Collections.unmodifiableSet(mChildren);
    }

    public <T extends Entity> Set<T> getChildren(Class<T> type)
    {
        Set<T> results = new HashSet<T>();
        for(Entity entity : mChildren)
        {
            if(type.isInstance(entity)) results.add(type.cast(entity));
        }
        return results;
    }

    public String getName()
    {
        if(mName == null)
            throw new IllegalStateException(
                    "Attempt to access Name before it has been set");
        return mName;
    }

    public void addSourceRelationship(Relationship rel)
    {
        if(rel == null)
            throw new IllegalArgumentException("Relationship cannot be null");
        if(!equals(rel.getSource()))
            throw new IllegalArgumentException(
                    "Attempt to add source relationship for which this object is not the source");
        if(!mSourceRelationships.add(rel))
            sLog.warn("Attempt to add same relationship twice: " + rel);
    }

    public void addTargetRelationship(Relationship rel)
    {
        if(rel == null)
            throw new IllegalArgumentException("Relationship cannot be null");
        if(!equals(rel.getTarget()))
            throw new IllegalArgumentException(
                    "Attempt to add target relationship for which this object is not the target");
        if(!mTargetRelationships.add(rel))
            sLog.warn("Attempt to add same relationship twice: " + rel);
    }

    public Relationship getSourceRelationship(RelationshipFilter filter)
    {
        Relationship result = null;
        Set<Relationship> rels = getSourceRelationships(filter);
        if(!rels.isEmpty()) result = rels.iterator().next();
        return result;
    }

    public Relationship getTargetRelationship(RelationshipFilter filter)
    {
        Relationship result = null;
        Set<Relationship> rels = getTargetRelationships(filter);
        if(!rels.isEmpty()) result = rels.iterator().next();
        return result;
    }

    public Set<Relationship> getSourceRelationships(RelationshipFilter filter)
    {
        return getRelationships(mSourceRelationships, filter);
    }

    public Set<Relationship> getTargetRelationships(RelationshipFilter filter)
    {
        return getRelationships(mTargetRelationships, filter);
    }

    public Set<Relationship> getSourceRelationships()
    {
        return Collections.unmodifiableSet(mSourceRelationships);
    }

    public Set<Relationship> getTargetRelationships()
    {
        return Collections.unmodifiableSet(mTargetRelationships);
    }

    public int compareTo(Entity e)
    {
        return mName.compareTo(e.getName());
    }

    protected Entity()
    {
        mSourceRelationships = new HashSet<Relationship>();
        mTargetRelationships = new HashSet<Relationship>();
    }

    protected void setName(String name)
    {
        if(mName != null)
            throw new IllegalStateException("Name already set and immutable");
        if(name == null)
            throw new IllegalArgumentException("Name cannot be null");
        mName = name;
    }

    protected void setParent(Entity parent)
    {
        mParent = parent;
    }

    protected void addChild(Entity child)
    {
        if(mChildren == null) mChildren = new HashSet<Entity>();
        mChildren.add(child);
    }

    private Set<Relationship> getRelationships(Set<Relationship> rels,
            RelationshipFilter filter)
    {
        Set<Relationship> result = new HashSet<Relationship>();

        for(Relationship rel : rels)
            if(filter.passes(rel)) result.add(rel);

        return result;
    }
}
