package com.rjcass.depends;

public interface EntityFactory
{
	Entity createEntity(EntityType type, String name);
}
