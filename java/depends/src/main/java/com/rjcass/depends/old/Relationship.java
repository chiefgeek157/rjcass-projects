package com.rjcass.depends.old;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Relationship
{
    public enum Type
    {
        DEPENDS_ON, EXTENDS, IMPLEMENTS, MEMBER_FIELD, LOCAL_FIELD, METHOD_PARAMETER;
    }

    private static Log sLog = LogFactory.getLog(Relationship.class);

    private Type mType;
    private Entity mSource;
    private Entity mTarget;
    private Set<Relationship> mCauses;
    private Set<Relationship> mGeneralizations;

    public Relationship(Type type, Entity source, Entity target)
    {
        mType = type;

        if(source == null)
            throw new IllegalArgumentException("Source cannot be null");
        if(target == null)
            throw new IllegalArgumentException("Target cannot be null");
        if(source.equals(target))
            throw new IllegalStateException(
                    "Source cannot be the same as target");
        mSource = source;
        mTarget = target;
    }

    public Type getType()
    {
        return mType;
    }

    public Entity getSource()
    {
        if(mSource == null)
            throw new IllegalStateException(
                    "Attempt to access source before it has been set");
        return mSource;
    }

    public <T extends Entity> T getSource(Class<T> type)
    {
        return type.cast(getSource());
    }

    public Entity getTarget()
    {
        if(mTarget == null)
            throw new IllegalStateException(
                    "Attempt to access target before it has been set");
        return mTarget;
    }

    public <T extends Entity> T getTarget(Class<T> type)
    {
        return type.cast(getTarget());
    }

    public void addCause(Relationship rel)
    {
        if(rel == null)
            throw new IllegalArgumentException("Relationship cannot be null");
        if(mCauses == null) mCauses = new HashSet<Relationship>();
        if(!mCauses.add(rel))
            sLog.warn("Attempt to add same relationship twice: " + rel);
    }

    public void addGeneralization(Relationship rel)
    {
        if(rel == null)
            throw new IllegalArgumentException("Relationship cannot be null");
        if(mGeneralizations == null)
            mGeneralizations = new HashSet<Relationship>();
        if(!mGeneralizations.add(rel))
            sLog.warn("Attempt to add same relationship twice: " + rel);
    }

    public Set<Relationship> getCauses()
    {
        Set<Relationship> result = mCauses;
        if(result == null) result = Collections.emptySet();
        return result;
    }

    public Set<Relationship> getGeneralizations()
    {
        Set<Relationship> result = mGeneralizations;
        if(result == null) result = Collections.emptySet();
        return result;
    }

    public boolean equals(Object obj)
    {
        sLog.debug("Comparing " + this + " and " + obj);
        boolean equal = false;
        if(obj != null && obj instanceof Relationship)
        {
            Relationship rel = (Relationship)obj;
            if(rel.mType == mType
                    && ((mSource == null && rel.mSource == null) || (mSource != null && mSource
                            .equals(rel.mSource)))
                    && ((mTarget == null && rel.mTarget == null) || (mTarget != null && mTarget
                            .equals(rel.mTarget)))) equal = true;
        }
        return equal;
    }

    public int hashCode()
    {
        return mType.hashCode() + mSource.hashCode() + mTarget.hashCode();
    }

    public String toString()
    {
        return new StringBuilder("Relationship[Type:").append(mType).append(
                ",Source:").append(mSource).append(",Target:").append(mTarget)
                .append("]").toString();
    }
}
