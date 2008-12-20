package com.rjcass.depends2.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.depends2.EntityType;
import com.rjcass.depends2.EntityTypeFactory;

public class TestBasicEntityTypeFactory
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
	public void testGetType()
	{
		EntityTypeFactory factory = new BasicEntityTypeFactory();
		EntityType type = factory.getType("TestType");
		assertNotNull(type);
		assertEquals(type.getTypeName(), "TestType");
	}

	@Test
	public void testGetTypeSingleton()
	{
		EntityTypeFactory factory = new BasicEntityTypeFactory();
		EntityType type1 = factory.getType("TestType");
		EntityType type2 = factory.getType("TestType");
		assertNotNull(type1);
		assertNotNull(type2);
		assertEquals(type1, type2);
	}
}
