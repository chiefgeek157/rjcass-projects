package com.rjcass.xbml.m2analyzer.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class XBMLNode extends XBMLProfiledEntity
{
	private XBMLNode mAnchor;
	private Set<XBMLAttributeStyle> mAttrStyles;
	private XBMLNodeBehavior mBehavior;
	private XBMLNodeStyle mNodeStyle;
	private XBMLObject mObject;
	private XBMLNode mTarget;

	public XBMLNode()
	{
		mAttrStyles = new HashSet<XBMLAttributeStyle>();
		setProjectPath(true);
	}

	public XBMLEntity realizePath(Queue<String> path)
	{
		throw new UnsupportedOperationException("Unsupported");
	}

	protected List<Set<? extends XBMLEntity>> getChildSets()
	{
		return Collections.emptyList();
	}

	public XBMLNode getAnchor()
	{
		return mAnchor;
	}

	public void setAnchor(XBMLNode anchor)
	{
		mAnchor = anchor;
	}

	public Set<XBMLAttributeStyle> getAttributeStyles()
	{
		return Collections.unmodifiableSet(mAttrStyles);
	}

	public void addAttributeStyle(XBMLAttributeStyle style)
	{
		mAttrStyles.add(style);
	}

	public XBMLNodeBehavior getBehavior()
	{
		return mBehavior;
	}

	public void setBehavior(XBMLNodeBehavior behavior)
	{
		mBehavior = behavior;
	}

	public XBMLNodeStyle getNodeStyle()
	{
		return mNodeStyle;
	}

	public void setNodeStyle(XBMLNodeStyle style)
	{
		mNodeStyle = style;
	}

	public XBMLObject getObject()
	{
		return mObject;
	}

	public void setObject(XBMLObject object)
	{
		mObject = object;
	}

	public XBMLNode getTarget()
	{
		return mTarget;
	}

	public void setTarget(XBMLNode target)
	{
		mTarget = target;
	}

	@Override
	public Set<XBMLEntity> getChildren()
	{
		Set<XBMLEntity> children = new HashSet<XBMLEntity>();
		if (mAnchor != null)
			children.add(mAnchor);
		children.addAll(mAttrStyles);
		if (mBehavior != null)
			children.add(mBehavior);
		if (mNodeStyle != null)
			children.add(mNodeStyle);
		if (mObject != null)
			children.add(mObject);
		if (mTarget != null)
			children.add(mTarget);
		return children;
	}
}
