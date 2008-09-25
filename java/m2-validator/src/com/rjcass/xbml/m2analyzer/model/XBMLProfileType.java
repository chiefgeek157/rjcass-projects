package com.rjcass.xbml.m2analyzer.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class XBMLProfileType extends XBMLEntity
{
	private Set<XBMLAttributeType> mAttrTypes;

	public XBMLProfileType()
	{
		mAttrTypes = new HashSet<XBMLAttributeType>();
	}

	public Set<XBMLAttributeType> getAttributeTypes()
	{
		return Collections.unmodifiableSet(mAttrTypes);
	}

	public void addAttributeType(XBMLAttributeType type)
	{
		mAttrTypes.add(type);
	}

	@Override
	public Set<XBMLEntity> getChildren()
	{
		Set<XBMLEntity> children = new HashSet<XBMLEntity>();
		children.addAll(mAttrTypes);
		return children;
	}
}
