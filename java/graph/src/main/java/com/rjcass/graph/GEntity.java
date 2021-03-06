package com.rjcass.graph;

import com.rjcass.commons.attribute.AttributeContainer;

public interface GEntity extends AttributeContainer
{
	String getId();

	void setName(String name);

	String getName();

	boolean isValid();
}
