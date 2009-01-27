package com.rjcass.depends.spi;

import com.rjcass.depends.Entity;

public interface SPIEntity extends Entity
{
	void addDependency(SPIDependency dependency);

	void removeDependency(SPIDependency dependency);

	void addDetail(SPIEntity entity);

	void removeDetail(SPIEntity entity);

	void setName(String name);
	
	void setType(SPIEntityType type);
}
