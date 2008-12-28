package com.rjcass.graph.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.graph.managed.ManagedArc;
import com.rjcass.graph.managed.ManagedEntityFactory;
import com.rjcass.graph.managed.ManagedEntityFactoryListener;
import com.rjcass.graph.managed.ManagedGraph;
import com.rjcass.graph.managed.ManagedNode;

public class BasicManagedEntityFactoryTest implements ManagedEntityFactoryListener
{
	private ManagedEntityFactory mFactory;
	private boolean mEventGraphCreated;
	private boolean mEventNodeCreated;
	private boolean mEventArcCreated;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{}

	@Before
	public void setUp() throws Exception
	{
		mFactory = new BasicManagedEntityFactory();
		mFactory.addListener(this);
	}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public void testCreateGraph()
	{
		ManagedGraph graph = mFactory.createGraph();
		assertEquals(BasicGraph.class, graph.getClass());
		assertTrue(mEventGraphCreated);
	}

	@Test
	public void testCreateNode()
	{
		ManagedNode node = mFactory.createNode();
		assertEquals(BasicNode.class, node.getClass());
		assertTrue(mEventNodeCreated);
	}

	@Test
	public void testCreateArc()
	{
		ManagedArc arc = mFactory.createArc();
		assertEquals(BasicArc.class, arc.getClass());
		assertTrue(mEventArcCreated);
	}

	public void graphCreated(ManagedGraph graph)
	{
		mEventGraphCreated = true;
	}

	public void nodeCreated(ManagedNode node)
	{
		mEventNodeCreated = true;
	}

	public void arcCreated(ManagedArc arc)
	{
		mEventArcCreated = true;
	}
}
