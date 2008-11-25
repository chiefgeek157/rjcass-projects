package com.rjcass.depends2;

public interface EntityFactory
{
	Entity createEntity(EntityType type, String name);
}
