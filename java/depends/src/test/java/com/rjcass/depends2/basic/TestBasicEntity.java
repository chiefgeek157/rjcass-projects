package com.rjcass.depends2.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.depends2.Entity;
import com.rjcass.depends2.EntityFactory;
import com.rjcass.depends2.EntityType;
import com.rjcass.depends2.EntityTypeFactory;
import com.rjcass.depends2.spi.SPIEntity;
import com.rjcass.depends2.spi.SPIEntityType;

public class TestBasicEntity
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{}

	private EntityTypeFactory mEntityTypeFactory;
	private EntityFactory mEntityFactory;
	private EntityType mType1;
	private EntityType mType2;
	private EntityType mType11;

	private EntityType mType12;

	private EntityType mType111;

	@Before
	public void setUp() throws Exception
	{
		mEntityTypeFactory = new BasicEntityTypeFactory();
		mEntityFactory = new GenericEntityFactory(BasicEntity.class.getName());

		mType1 = mEntityTypeFactory.getType("Type 1");
		mType2 = mEntityTypeFactory.getType("Type 2");
		mType11 = mEntityTypeFactory.getType("Type 1.1");
		mType12 = mEntityTypeFactory.getType("Type 1.2");
		mType111 = mEntityTypeFactory.getType("Type 1.1.1");

		((BasicEntityType)mType11).setSuperType((SPIEntityType)mType1);
		((BasicEntityType)mType12).setSuperType((SPIEntityType)mType1);
		((BasicEntityType)mType111).setSuperType((SPIEntityType)mType11);

		((BasicEntityType)mType1).addPropertyName("Property 1");
		((BasicEntityType)mType2).addPropertyName("Property 2");
		((BasicEntityType)mType11).addPropertyName("Property 1.1");
		((BasicEntityType)mType12).addPropertyName("Property 1.2");
		((BasicEntityType)mType111).addPropertyName("Property 1.1.1");
	}

	@After
	public void tearDown() throws Exception
	{}

	@Test(expected = IllegalArgumentException.class)
	public void testAbstractionCycle() throws IllegalArgumentException
	{
		Entity entity1 = mEntityFactory.createEntity(mType1, "Entity 1");
		Entity entity2 = mEntityFactory.createEntity(mType2, "Entity 2");
		Entity entity11 = mEntityFactory.createEntity(mType11, "Entity 11");

		((SPIEntity)entity2).setAbstraction(entity1);
		((SPIEntity)entity11).setAbstraction(entity2);
		((SPIEntity)entity1).setAbstraction(entity11);
	}

	@Test
	public void testAbstractions()
	{
		Entity entity1 = mEntityFactory.createEntity(mType1, "Entity 1");
		Entity entity2 = mEntityFactory.createEntity(mType2, "Entity 2");
		Entity entity11 = mEntityFactory.createEntity(mType11, "Entity 11");

		((SPIEntity)entity2).setAbstraction(entity1);
		((SPIEntity)entity11).setAbstraction(entity2);

		assertEquals(entity1.getLevelOfAbstraction(), 0);
		assertEquals(entity2.getLevelOfAbstraction(), 1);
		assertEquals(entity11.getLevelOfAbstraction(), 2);

		assertNull(entity1.getAbstraction());
		assertEquals(entity1, entity2.getAbstraction());
		assertEquals(entity2, entity11.getAbstraction());
	}

	@Test
	public void testDetails()
	{}

	@Test
	public void testGetProperties()
	{}

	@Test
	public void testRemoveProperty()
	{}

	@Test
	public void testSetProperty()
	{}
}
