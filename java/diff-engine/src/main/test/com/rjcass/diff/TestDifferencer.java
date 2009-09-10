package com.rjcass.diff;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjcass.diff.index.ListIndex;
import com.rjcass.diff.index.MemoryFileIndex;

public class TestDifferencer
{
	private ListIndex<String> mIndex1;
	private ListIndex<String> mIndex2;
	private DiffAccumulator<String> mAccum;
	private Comparator<String> mComparator;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{}

	@Before
	public void setUpBefore() throws Exception
	{
		mAccum = new DiffAccumulator<String>();
		mComparator = new Comparator<String>()
		{
			public int compare(String s1, String s2)
			{
				return s1.compareTo(s2);
			}
		};

		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();

		list1.add("A");
		list1.add("B");
		list1.add("C");
		list1.add("A");
		list1.add("B");
		list1.add("B");
		list1.add("A");

		list2.add("C");
		list2.add("B");
		list2.add("A");
		list2.add("B");
		list2.add("A");
		list2.add("C");

		mIndex1 = new ListIndex<String>(list1);
		mIndex2 = new ListIndex<String>(list2);
	}

	@Test
	public void testAtoA() throws IOException
	{
		DifferenceEngine diff = DifferenceEngine.getInstance();
		int editDist = diff.findMinEditDistance(mIndex1, mIndex1, mComparator, 7, mAccum);

		mAccum.dumpResults(System.out);

		assertEquals(editDist, 0);
	}

	@Test
	public void testBtoB() throws IOException
	{
		DifferenceEngine diff = DifferenceEngine.getInstance();
		int editDist = diff.findMinEditDistance(mIndex2, mIndex2, mComparator, 6, mAccum);

		mAccum.dumpResults(System.out);

		assertEquals(editDist, 0);
	}

	@Test
	public void testAtoB() throws IOException
	{
		DifferenceEngine diff = DifferenceEngine.getInstance();
		int editDist = diff.findMinEditDistance(mIndex1, mIndex2, mComparator, 7, mAccum);

		mAccum.dumpResults(System.out);

		assertEquals(editDist, 5);
	}

	@Test
	public void testBtoA() throws IOException
	{
		DifferenceEngine diff = DifferenceEngine.getInstance();
		int editDist = diff.findMinEditDistance(mIndex2, mIndex1, mComparator, 7, mAccum);

		mAccum.dumpResults(System.out);

		assertEquals(editDist, 5);
	}

	@Test
	public void testTwoFiles() throws Exception
	{
		MemoryFileIndex file1 = new MemoryFileIndex("samples/Copy Book 1.txt");
		MemoryFileIndex file2 = new MemoryFileIndex("samples/Copy Book 2.txt");

		DifferenceEngine diff = DifferenceEngine.getInstance();
		int editDist = diff.findMinEditDistance(file1, file2, mComparator, Math.max(file1.size(), file2.size()), mAccum);

		mAccum.dumpResults(System.out);

		assertEquals(editDist, 9);
	}
}
