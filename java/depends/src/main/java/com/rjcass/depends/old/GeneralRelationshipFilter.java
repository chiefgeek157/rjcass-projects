/**
 * 
 */
package com.rjcass.depends.old;

import java.util.HashSet;
import java.util.Set;

public class GeneralRelationshipFilter implements RelationshipFilter
{
	private Relationship.Type mType;
	private Set<Class<? extends Entity>> mSourceClasses;
	private Set<Class<? extends Entity>> mTargetClasses;

	public GeneralRelationshipFilter()
	{}

	public GeneralRelationshipFilter(Relationship.Type type)
	{
		requireType(type);
	}

	public GeneralRelationshipFilter(Relationship.Type type, Class<? extends Entity> sourceClass,
			Class<? extends Entity> targetClass)
	{
		requireType(type);
		addAllowableSourceClass(sourceClass);
		addAllowableTargetClass(targetClass);
	}

	public void requireType(Relationship.Type type)
	{
		if (type == null)
			throw new IllegalArgumentException("Type cannot be null");
		mType = type;
	}

	public void addAllowableSourceClass(Class<? extends Entity> cls)
	{
		if (cls == null)
			throw new IllegalArgumentException("Source class cannot be null");
		if (mSourceClasses == null)
			mSourceClasses = new HashSet<Class<? extends Entity>>();
		mSourceClasses.add(cls);
	}

	public void addAllowableTargetClass(Class<? extends Entity> cls)
	{
		if (cls == null)
			throw new IllegalArgumentException("Source class cannot be null");
		if (mTargetClasses == null)
			mTargetClasses = new HashSet<Class<? extends Entity>>();
		mTargetClasses.add(cls);
	}

	public boolean passes(Relationship rel)
	{
		boolean passes = true;
		if (mType != null)
		{
			if (rel.getType() != mType)
				passes = false;
		}
		if (passes)
		{
			if (mSourceClasses != null)
			{
				passes = false;
				Class<? extends Entity> sourceCls = rel.getSource().getClass();
				for (Class<? extends Entity> cls : mSourceClasses)
				{
					if (cls == sourceCls)
					{
						passes = true;
						break;
					}
				}
			}
		}
		if (passes)
		{
			if (mTargetClasses != null)
			{
				passes = false;
				Class<? extends Entity> targetCls = rel.getTarget().getClass();
				for (Class<? extends Entity> cls : mTargetClasses)
				{
					if (cls == targetCls)
					{
						passes = true;
						break;
					}
				}
			}
		}
		return passes;
	}
}