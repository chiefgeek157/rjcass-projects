package com.rjcass.depends;

import java.util.Set;

public interface EntityType
{
	String getTypeName();

	EntityType getSuperType();

	boolean isSubtypeOf(EntityType type);

	Set<String> getPropertyNames();

	boolean validatePropertyName(String name);
}
