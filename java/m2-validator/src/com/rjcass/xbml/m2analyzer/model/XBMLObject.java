package com.rjcass.xbml.m2analyzer.model;

import java.util.HashSet;
import java.util.Set;

public class XBMLObject extends XBMLProfiledEntity
{
	private XBMLProfileType mProfileType;

	public XBMLObject()
	{
	}

	public XBMLProfileType getProfileType()
	{
		return mProfileType;
	}

	public void setProfileType(XBMLProfileType profileType)
	{
		mProfileType = profileType;
	}

	@Override
	public Set<XBMLEntity> getChildren()
	{
		Set<XBMLEntity> children = new HashSet<XBMLEntity>();
		if (mProfileType != null)
			children.add(mProfileType);
		return children;
	}
}
