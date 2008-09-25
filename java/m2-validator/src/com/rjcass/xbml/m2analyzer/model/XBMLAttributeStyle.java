package com.rjcass.xbml.m2analyzer.model;

import java.util.HashSet;
import java.util.Set;

public class XBMLAttributeStyle extends XBMLEntity
{
	private XBMLAttributeType mType;

	public XBMLAttributeStyle()
	{}

	public XBMLAttributeType getType()
	{
		return mType;
	}

	public void setType(XBMLAttributeType type)
	{
		mType = type;
	}

	@Override
	public Set<XBMLEntity> getChildren()
	{
		Set<XBMLEntity> children = new HashSet<XBMLEntity>();
		if (mType != null)
			children.add(mType);
		return children;
	}
}
