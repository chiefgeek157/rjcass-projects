package com.rjcass.commons.attribute;

public abstract class AbstractAttributeContainer implements AttributeContainer
{
	private AttributeContainerDelegate mDelegate;

	public AbstractAttributeContainer()
	{
		mDelegate = new AttributeContainerDelegate();
	}

	public Object getAttribute(String name)
	{
		return mDelegate.getAttribute(name);
	}

	public boolean getAttributeAsBoolean(String name)
	{
		return mDelegate.getAttributeAsBoolean(name);
	}

	public double getAttributeAsDouble(String name)
	{
		return mDelegate.getAttributeAsDouble(name);
	}

	public int getAttributeAsInt(String name)
	{
		return mDelegate.getAttributeAsInt(name);
	}

	public String getAttributeAsString(String name)
	{
		return mDelegate.getAttributeAsString(name);
	}

	public void setAttribute(String name, Object value)
	{
		mDelegate.setAttribute(name, value);
	}
}
