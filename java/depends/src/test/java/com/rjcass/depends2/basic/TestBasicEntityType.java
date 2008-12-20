package com.rjcass.depends2.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.depends2.EntityType;
import com.rjcass.depends2.EntityTypeFactory;
import com.rjcass.depends2.spi.SPIEntityType;

public class TestBasicEntityType
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
	public void testSuperType()
	{
		EntityTypeFactory factory = new BasicEntityTypeFactory();

		EntityType superType = factory.getType("SuperType");
		EntityType sub1Type = factory.getType("Sub1Type");
		EntityType sub2Type = factory.getType("Sub2Type");
		EntityType sub3Type = factory.getType("Sub3Type");
		EntityType sub4Type = factory.getType("Sub4Type");

		((BasicEntityType)sub1Type).setSuperType((SPIEntityType)superType);
		((BasicEntityType)sub2Type).setSuperType((SPIEntityType)sub1Type);
		((BasicEntityType)sub3Type).setSuperType((SPIEntityType)sub2Type);
		((BasicEntityType)sub4Type).setSuperType((SPIEntityType)sub3Type);

		assertEquals(superType, sub1Type.getSuperType());
		assertEquals(sub1Type, sub2Type.getSuperType());
		assertEquals(sub2Type, sub3Type.getSuperType());
		assertEquals(sub3Type, sub4Type.getSuperType());

		assertTrue(sub1Type.isSubtypeOf(superType));
		assertTrue(sub2Type.isSubtypeOf(superType));
		assertTrue(sub3Type.isSubtypeOf(superType));
		assertTrue(sub4Type.isSubtypeOf(superType));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCyclicSuperType() throws IllegalArgumentException
	{
		EntityTypeFactory factory = new BasicEntityTypeFactory();

		EntityType superType = factory.getType("SuperType");
		EntityType sub1Type = factory.getType("Sub1Type");
		EntityType sub2Type = factory.getType("Sub2Type");

		((BasicEntityType)sub1Type).setSuperType((SPIEntityType)superType);
		((BasicEntityType)sub2Type).setSuperType((SPIEntityType)sub1Type);
		((BasicEntityType)superType).setSuperType((SPIEntityType)sub2Type);
	}

	@Test
	public void testProperties()
	{
		EntityTypeFactory factory = new BasicEntityTypeFactory();

		EntityType type1 = factory.getType("AnyType");
		((BasicEntityType)type1).addPropertyName("PropertyName1");
		((BasicEntityType)type1).addPropertyName("PropertyName2");
		((BasicEntityType)type1).addPropertyName("PropertyName3");

		assertTrue(type1.validatePropertyName("PropertyName1"));
		assertTrue(type1.validatePropertyName("PropertyName2"));
		assertTrue(type1.validatePropertyName("PropertyName3"));

		Set<String> propertyNames1 = type1.getPropertyNames();
		assertEquals(propertyNames1.size(), 3);

		EntityType type2 = factory.getType("SubType");
		((BasicEntityType)type2).setSuperType((SPIEntityType)type1);

		((BasicEntityType)type2).addPropertyName("PropertyName4");

		assertTrue(type2.validatePropertyName("PropertyName4"));
		assertTrue(type2.validatePropertyName("PropertyName1"));

		Set<String> propertyNames2 = type2.getPropertyNames();
		assertEquals(propertyNames2.size(), 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCannotAddPropertyTwice() throws IllegalArgumentException
	{
		EntityTypeFactory factory = new BasicEntityTypeFactory();

		EntityType type1 = factory.getType("AnyType");
		((BasicEntityType)type1).addPropertyName("PropertyName1");

		EntityType type2 = factory.getType("SubType");
		((BasicEntityType)type2).setSuperType((SPIEntityType)type1);

		((BasicEntityType)type2).addPropertyName("PropertyName1");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCannotAddSuperWithSameProperty() throws IllegalArgumentException
	{
		EntityTypeFactory factory = new BasicEntityTypeFactory();

		EntityType type1 = factory.getType("AnyType");
		((BasicEntityType)type1).addPropertyName("PropertyName1");

		EntityType type2 = factory.getType("SubType");
		((BasicEntityType)type2).addPropertyName("PropertyName1");

		((BasicEntityType)type2).setSuperType((SPIEntityType)type1);
	}
}
