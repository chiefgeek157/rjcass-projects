package com.rjcass.depends2.basic;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.depends2.Entity;
import com.rjcass.depends2.EntityFactory;
import com.rjcass.depends2.EntityType;
import com.rjcass.depends2.EntityTypeFactory;

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

		EntityFactory factory = new GenericEntityFactory("BasicEntity");
		Entity entity1 = factory.createEntity(type1, "Entity1");

		assertNotNull(entity1);
	}
}
