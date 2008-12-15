package com.rjcass.depends2;

import java.util.Set;

public interface DependencyType
{
	Set<DependencyType> getSuperTypes();

	String getName();
}
