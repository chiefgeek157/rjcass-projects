package com.rjcass.depends2.basic;

import java.util.HashMap;
import java.util.Map;

import com.rjcass.depends2.EntityType;
import com.rjcass.depends2.EntityTypeFactory;

public class BasicEntityTypeFactory implements EntityTypeFactory
{
	private Map<String, EntityType> mTypes = new HashMap<String, EntityType>();

	public BasicEntityTypeFactory()
	{
		mTypes = new HashMap<String, EntityType>();
	}

	@Override
	public final EntityType getType(String name)
	{
		if (name == null)
			throw new IllegalArgumentException("Type cannot be null");

		EntityType type = mTypes.get(name);
		if (type == null)
		{
			type = createEntityType(name);
			mTypes.put(name, type);
		}
		return type;
	}

	protected EntityType createEntityType(String name)
	{
		return new BasicEntityType(name);
	}
}
