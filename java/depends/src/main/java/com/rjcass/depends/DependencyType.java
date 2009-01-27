package com.rjcass.depends;

import java.util.Set;

public interface DependencyType
{
	Set<DependencyType> getSuperTypes();

	String getName();
}
