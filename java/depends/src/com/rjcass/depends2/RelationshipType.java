package com.rjcass.depends2;

import java.util.Set;

public interface RelationshipType
{
	Set<RelationshipType> getSuperTypes();
	String getName();
}
