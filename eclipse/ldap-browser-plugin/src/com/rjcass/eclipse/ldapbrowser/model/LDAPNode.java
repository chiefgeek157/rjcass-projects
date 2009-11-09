package com.rjcass.eclipse.ldapbrowser.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LDAPNode
{
	private LDAPModel		mModel;
	private LDAPNode		mParent;
	private List<LDAPNode>	mChildren;
	private boolean			mChildrenQueried;
	private String			mName;

	public LDAPNode()
	{}

	public LDAPNode(LDAPModel model)
	{
		mModel = model;
	}

	public LDAPNode(String name)
	{
		mName = name;
	}

	public LDAPNode(LDAPModel model, String name)
	{
		mModel = model;
		mName = name;
	}

	public LDAPNode getRoot()
	{
		if (mParent == null)
			return this;
		else
			return mParent.getRoot();
	}

	public LDAPNode getParent()
	{
		return mParent;
	}

	public void setParent(LDAPNode parent)
	{
		mParent = parent;
	}

	public LDAPModel getModel()
	{
		return mModel;
	}

	public void setModel(LDAPModel model)
	{
		mModel = model;
	}

	public boolean hasChildren()
	{
		return true;
	}

	public List<LDAPNode> getChildren()
	{
		if (!mChildrenQueried)
		{
			getModel().addChildren(this);
			mChildrenQueried = true;
		}
		if (mChildren == null)
			return Collections.emptyList();
		else
			return Collections.unmodifiableList(mChildren);
	}

	public void addChild(LDAPNode child)
	{
		createChildList();
		mChildren.add(child);
		child.setParent(this);
	}

	public String getName()
	{
		return mName;
	}

	public String getDN()
	{
		return mName + "," + getParent().getDN();
	}

	protected void setName(String name)
	{
		mName = name;
	}

	private void createChildList()
	{
		if (mChildren == null)
			mChildren = new ArrayList<LDAPNode>();
	}
}