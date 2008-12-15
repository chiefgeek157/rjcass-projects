package com.rjcass.depends2.spi;

import com.rjcass.depends2.Dependency;
import com.rjcass.depends2.Entity;

public interface SPIEntity extends Entity
{
	void addDependency(Dependency dependency);

	void removeDependency(Dependency dependency);

	void addDetail(SPIEntity entity);

	void removeDetail(SPIEntity entity);
}
