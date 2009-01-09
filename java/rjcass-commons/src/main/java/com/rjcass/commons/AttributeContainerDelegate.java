package com.rjcass.commons;

import java.util.HashMap;
import java.util.Map;

public class AttributeContainerDelegate implements AttributeContainer
{
	private Map<String, Object> mAttributes;

	public AttributeContainerDelegate()
	{}

	public Object getAttribute(String name)
	{
		Object result = null;
		if (mAttributes != null)
			result = mAttributes.get(name);
		return result;
	}

	public boolean getAttributeAsBoolean(String name)
	{
		boolean result = false;
		if (mAttributes != null)
		{
			Object obj = mAttributes.get(name);
			if (obj != null && obj instanceof Boolean)
				result = ((Boolean)obj).booleanValue();
		}
		return result;
	}

	public double getAttributeAsDouble(String name)
	{
		double result = 0.0;
		if (mAttributes != null)
		{
			Object obj = mAttributes.get(name);
			if (obj != null && obj instanceof Double)
				result = ((Double)obj).doubleValue();
		}
		return result;
	}

	public int getAttributeAsInt(String name)
	{
		int result = 0;
		if (mAttributes != null)
		{
			Object obj = mAttributes.get(name);
			if (obj != null && obj instanceof Integer)
				result = ((Integer)obj).intValue();
		}
		return result;
	}

	public String getAttributeAsString(String name)
	{
		String result = null;
		if (mAttributes != null)
		{
			Object obj = mAttributes.get(name);
			if (obj != null && obj instanceof String)
				result = (String)obj;
		}
		return result;
	}

	public void setAttribute(String name, Object value)
	{
		createAttributeMap();
		mAttributes.put(name, value);
	}

	private void createAttributeMap()
	{
		if (mAttributes == null)
			mAttributes = new HashMap<String, Object>();
	}
}
