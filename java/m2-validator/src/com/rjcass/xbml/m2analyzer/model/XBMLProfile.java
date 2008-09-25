package com.rjcass.xbml.m2analyzer.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class XBMLProfile extends XBMLEntity
{
	private XBMLProfileType mType;
	private Set<XBMLAttributeType> mAttributeTypes;

	public XBMLProfile()
	{
		mAttributeTypes = new HashSet<XBMLAttributeType>();
	}

	public XBMLProfileType getType()
	{
		return mType;
	}

	public void setType(XBMLProfileType type)
	{
		mType = type;
	}

	public Set<XBMLAttributeType> getAttributeTypes()
	{
		return Collections.unmodifiableSet(mAttributeTypes);
	}

	public void addAttributeType(XBMLAttributeType attributeType)
	{
		mAttributeTypes.add(attributeType);
	}

	@Override
	public Set<XBMLEntity> getChildren()
	{
		Set<XBMLEntity> children = new HashSet<XBMLEntity>();
		if (mType != null)
			children.add(mType);
		children.addAll(mAttributeTypes);
		return children;
	}
}
