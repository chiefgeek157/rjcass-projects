package com.rjcass.xbml.m2analyzer.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.rjcass.xbml.m2analyzer.M2AnalyzerException;

public class XBMLView extends XBMLEntity
{
	private XBMLNode mRootItem;
	private Set<XBMLView> mViews;
	private Set<XBMLLink> mLinks;
	private Set<XBMLNode> mNodes;

	public XBMLView()
	{
		mViews = new HashSet<XBMLView>();
		mLinks = new HashSet<XBMLLink>();
		mNodes = new HashSet<XBMLNode>();
		setProjectPath(true);
	}

	public XBMLNode getRootItem()
	{
		return mRootItem;
	}

	public void setRootItem(XBMLNode rootItem)
	{
		mRootItem = rootItem;
	}

	public void addView(XBMLView view)
	{
		mViews.add(view);
	}

	public void addLink(XBMLLink link)
	{
		mLinks.add(link);
	}

	public void addNode(XBMLNode node)
	{
		mNodes.add(node);
	}

	public Set<XBMLView> getViews()
	{
		return Collections.unmodifiableSet(mViews);
	}

	public Set<XBMLLink> getLinks()
	{
		return Collections.unmodifiableSet(mLinks);
	}

	public Set<XBMLNode> getNodes()
	{
		return Collections.unmodifiableSet(mNodes);
	}

	@Override
	public Set<XBMLEntity> getChildren()
	{
		Set<XBMLEntity> children = new HashSet<XBMLEntity>();
		if (mRootItem != null)
			children.add(mRootItem);
		children.addAll(mViews);
		children.addAll(mLinks);
		children.addAll(mNodes);
		return children;
	}

	@Override
	public void addChild(XBMLEntity entity)
	{
		if (entity instanceof XBMLLink)
		{
			mLinks.add((XBMLLink)entity);
		}
		else if (entity instanceof XBMLNode)
		{
			mNodes.add((XBMLNode)entity);
		}
		else if (entity instanceof XBMLView)
		{
			mViews.add((XBMLView)entity);
		}
		else
		{
			throw new M2AnalyzerException("Wrong type of child: " + entity);
		}
	}
}
