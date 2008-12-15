package com.rjcass.depends2.basic;

import com.rjcass.depends2.Entity;
import com.rjcass.depends2.EntityFactory;
import com.rjcass.depends2.EntityType;

public class BasicEntityFactory implements EntityFactory
{
	@Override
	public Entity createEntity(EntityType type, String name)
	{
		BasicSPIEntity entity = new BasicSPIEntity(type, name);
		return entity;
	}
}
