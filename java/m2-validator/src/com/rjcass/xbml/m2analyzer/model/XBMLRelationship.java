package com.rjcass.xbml.m2analyzer.model;

import java.util.HashSet;
import java.util.Set;

public class XBMLRelationship extends XBMLProfiledEntity
{
	private XBMLObject mOrigin;
	private XBMLObject mDestination;

	public XBMLRelationship()
	{}

	public XBMLObject getOrigin()
	{
		return mOrigin;
	}

	public void setOrigin(XBMLObject origin)
	{
		mOrigin = origin;
	}

	public XBMLObject getDestination()
	{
		return mDestination;
	}

	public void setDestination(XBMLObject destination)
	{
		mDestination = destination;
	}

	@Override
	public Set<XBMLEntity> getChildren()
	{
		Set<XBMLEntity> children = new HashSet<XBMLEntity>();
		if (mOrigin != null)
			children.add(mOrigin);
		if (mDestination != null)
			children.add(mDestination);
		return children;
	}
}
