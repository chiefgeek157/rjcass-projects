package com.rjcass.diff;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.diff.index.MemoryFileIndex;

public class TestMemoryFileIndex
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
	public void testMemoryFileIndex() throws Exception
	{
		MemoryFileIndex index = new MemoryFileIndex("samples/Copy Book 1.txt");
		Assert.assertEquals(index.size(), 10291);
	}
}
