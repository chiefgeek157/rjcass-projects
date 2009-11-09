package com.rjcass.eclipse.ldapbrowser.model;

public class AttributeValue implements Comparable<AttributeValue>
{
	private String	mName;
	private String	mValue;

	public AttributeValue(String name, String value)
	{
		if (name == null || value == null)
			throw new NullPointerException("Name and value cannotbe null");

		mName = name;
		mValue = value;
	}

	public String getName()
	{
		return mName;
	}

	public String getValue()
	{
		return mValue;
	}

	@Override
	public int compareTo(AttributeValue av)
	{
		if (av == null)
			throw new NullPointerException();

		int result = mName.compareToIgnoreCase(av.mName);
		if (result == 0)
			result = mValue.compareToIgnoreCase(av.mValue);

		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj != null && obj instanceof AttributeValue)
		{
			AttributeValue av = (AttributeValue)obj;
			return (mName.equals(av.mName) && mValue.equals(av.mValue));
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return (mName + mValue).hashCode();
	}

	@Override
	public String toString()
	{
		return "(" + mName + "=" + mValue + ")";
	}
}
