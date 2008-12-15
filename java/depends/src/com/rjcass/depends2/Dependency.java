package com.rjcass.depends2;

import java.util.Set;

public interface Dependency
{
	DependencyType getType();

	void setSource(Entity entity);

	Entity getSource();

	void setTarget(Entity entity);

	Entity getTarget();

	Entity getOtherEntity(Entity entity);

	void setAbstraction(Dependency dependency);

	Dependency getAbstraction();

	int getLevelOfAbstraction();

	Set<Dependency> getDetails();

	boolean isDetailOf(Entity entity);
}
