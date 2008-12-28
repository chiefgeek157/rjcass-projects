package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.graph.Model;
import com.rjcass.graph.ModelFactoryListener;

public class BasicModelFactoryTest implements ModelFactoryListener
{
	private BasicModelFactory mFactory;
	private boolean mEventModelCreated;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{}

	@Before
	public void setUp() throws Exception
	{
		mFactory = new BasicModelFactory();
	}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public void testCreateModel()
	{
		Model model = mFactory.createModel();
		assertNotNull(model);
		assertEquals(BasicModel.class, model.getClass());
	}

	@Test
	public void testModelListener()
	{
		mFactory.addModelFactoryListener(this);
		mFactory.createModel();
		assertTrue(mEventModelCreated);
	}

	public void modelCreated(Model model)
	{
		mEventModelCreated = true;
	}
}
