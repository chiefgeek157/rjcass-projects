package com.rjcass.xbml.m2analyzer.model;

import java.util.HashSet;
import java.util.Set;

public class XBMLLink extends XBMLEntity
{
	private XBMLLinkBehavior mBehavior;
	private XBMLNode mDestination;
	private XBMLNode mOrigin;
	private XBMLRelationship mRelationship;
	private XBMLLinkStyle mStyle;
	private XBMLLink mTarget;

	public XBMLLink()
	{
		setProjectPath(true);
	}

	public XBMLLinkBehavior getBehavior()
	{
		return mBehavior;
	}

	public void setBehavior(XBMLLinkBehavior behavior)
	{
		mBehavior = behavior;
	}

	public XBMLNode getDestination()
	{
		return mDestination;
	}

	public void setDestination(XBMLNode destination)
	{
		mDestination = destination;
	}

	public XBMLNode getOrigin()
	{
		return mOrigin;
	}

	public void setOrigin(XBMLNode origin)
	{
		mOrigin = origin;
	}

	public XBMLRelationship getRelationship()
	{
		return mRelationship;
	}

	public void setRelationship(XBMLRelationship realtionship)
	{
		mRelationship = realtionship;
	}

	public XBMLLinkStyle getStyle()
	{
		return mStyle;
	}

	public void setStyle(XBMLLinkStyle style)
	{
		mStyle = style;
	}

	public XBMLLink getTarget()
	{
		return mTarget;
	}

	public void setTarget(XBMLLink link)
	{
		mTarget = link;
	}

	@Override
	public Set<XBMLEntity> getChildren()
	{
		Set<XBMLEntity> children = new HashSet<XBMLEntity>();
		if (mBehavior != null)
			children.add(mBehavior);
		if (mDestination != null)
			children.add(mDestination);
		if (mOrigin != null)
			children.add(mOrigin);
		if (mRelationship != null)
			children.add(mRelationship);
		if (mStyle != null)
			children.add(mStyle);
		if (mTarget != null)
			children.add(mTarget);
		return children;
	}

	// public XBMLEntity realizePath(Queue<String> path)
	// {
	// throw new UnsupportedOperationException("Unsupported");
	// }
	//
	// protected List<Set<? extends XBMLEntity>> getChildSets()
	// {
	// return Collections.emptyList();
	// }
}
