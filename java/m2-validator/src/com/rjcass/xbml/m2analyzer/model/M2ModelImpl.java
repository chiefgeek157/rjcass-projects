package com.rjcass.xbml.m2analyzer.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.rjcass.xbml.m2analyzer.M2Model;

public class M2ModelImpl implements M2Model
{
	private XBMLEntity mRoot;
	private Set<XBMLEntity> mAllEntities;
	private Set<XBMLEntity> mVisited;
	private Set<XBMLEntity> mHasMissingChild;
	private Set<XBMLEntity> mMissingChildren;

	public M2ModelImpl(XBMLEntity root, Collection<XBMLEntity> values, Collection<XBMLEntity> visited,
			Collection<XBMLEntity> hasMissingChildren, Collection<XBMLEntity> missingChildren)
	{
		mRoot = root;
		mAllEntities = new HashSet<XBMLEntity>();
		mVisited = new HashSet<XBMLEntity>();
		mHasMissingChild = new HashSet<XBMLEntity>();
		mMissingChildren = new HashSet<XBMLEntity>();

		mAllEntities.addAll(values);
		mVisited.addAll(visited);
		mHasMissingChild.addAll(hasMissingChildren);
		mMissingChildren.addAll(missingChildren);
	}

	public XBMLEntity getRoot()
	{
		return mRoot;
	}

	public Set<XBMLEntity> getAllEntities()
	{
		return Collections.unmodifiableSet(mAllEntities);
	}

	public Set<XBMLEntity> getVisited()
	{
		return Collections.unmodifiableSet(mVisited);
	}

	public Set<XBMLEntity> getHasMissingChildren()
	{
		return Collections.unmodifiableSet(mHasMissingChild);
	}

	public Set<XBMLEntity> getMissingChildren()
	{
		return Collections.unmodifiableSet(mMissingChildren);
	}
}
