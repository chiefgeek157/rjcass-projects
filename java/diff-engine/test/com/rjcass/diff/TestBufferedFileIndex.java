package com.rjcass.diff;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.diff.index.BufferedFileIndex;

public class TestBufferedFileIndex
{
	private BufferedFileIndex mIndex;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{}

	@Before
	public void setUp() throws Exception
	{
		mIndex = new BufferedFileIndex("samples/Copy Book 1.txt");
	}

	@After
	public void tearDown() throws Exception
	{
		mIndex.dispose();
	}

	@Test
	public void testBufferedFileIndex() throws Exception
	{}

	@Test
	public void testGet() throws Exception
	{
		System.out.println("Line 10: " + mIndex.get(100));
	}

	@Test
	public void testSize() throws Exception
	{
		assertEquals(mIndex.size(), 10291);
	}
}
