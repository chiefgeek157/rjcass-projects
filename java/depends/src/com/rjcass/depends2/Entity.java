package com.rjcass.depends2;

import java.util.Map;
import java.util.Set;

public interface Entity
{
	EntityType getType();

	void setAbstraction(Entity entity);

	int getLevelOfAbstraction();

	Entity getAbstraction();

	Set<Entity> getDetails();

	boolean isDetailOf(Entity entity);

	String getName();

	void setProperty(String name, String value);

	String getProperty(String name);

	Map<String, String> getProperties();

	void removeProperty(String name);
}
