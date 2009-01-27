package com.rjcass.depends2.basic;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.depends.Entity;
import com.rjcass.depends.EntityFactory;
import com.rjcass.depends.EntityType;
import com.rjcass.depends.EntityTypeFactory;
import com.rjcass.depends.basic.BasicEntity;
import com.rjcass.depends.basic.BasicEntityTypeFactory;
import com.rjcass.depends.basic.GenericEntityFactory;

public class TestBasicEntityFactory
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{}

	@Before
	public void setUp() throws Exception
	{}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public void testCreateEntity()
	{
		EntityTypeFactory typeFactory = new BasicEntityTypeFactory();
		EntityType type1 = typeFactory.getType("Type1");

		EntityFactory factory = new GenericEntityFactory(BasicEntity.class.getName());
		Entity entity1 = factory.createEntity(type1, "Entity1");

		assertNotNull(entity1);
	}
}
