package com.rjcass.commons.attribute;

public interface AttributeContainer
{
	void setAttribute(String name, Object value);

	Object getAttribute(String name);

	String getAttributeAsString(String name);

	int getAttributeAsInt(String name);

	double getAttributeAsDouble(String name);

	boolean getAttributeAsBoolean(String name);
}
