package com.rjcass.depends;

import java.util.Map;
import java.util.Set;

public interface Entity
{
	EntityType getType();

	Set<? extends Dependency> getDependencies();

	Set<? extends Dependency> getDependents();

	Set<? extends Entity> getDependencyEntities();

	Set<? extends Entity> getDependentEntities();

	boolean dependsOn(Entity entity);

	boolean dependsOn(Entity entity, boolean transiency);

	void setAbstraction(Entity entity);

	int getLevelOfAbstraction();

	Entity getAbstraction();

	Set<? extends Entity> getDetails();

	boolean isDetailOf(Entity entity);

	String getName();

	void setProperty(String name, String value);

	String getProperty(String name);

	Map<String, String> getProperties();

	void removeProperty(String name);
}
