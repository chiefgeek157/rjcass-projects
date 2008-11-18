package com.rjcass.diff;

import java.util.Comparator;

import com.rjcass.diff.DiffListener.DiffAction;

public class DifferenceEngine
{
	public static DifferenceEngine getInstance()
	{
		return new DifferenceEngine();
	}

	public <T> int findMinEditDistance(Index<T> index1, Index<T> index2, Comparator<T> comparator)
	{
		return findMinEditDistance(index1, index2, comparator, Math.max(index1.size(), index2.size()), null);
	}

	public <T> int findMinEditDistance(Index<T> index1, Index<T> index2, Comparator<T> comparator, int maxDistance)
	{
		return findMinEditDistance(index1, index2, comparator, maxDistance, null);
	}

	public <T> int findMinEditDistance(Index<T> index1, Index<T> index2, Comparator<T> comparator, int maxDistance,
			DiffListener<T> listener)
	{
		int size1 = index1.size();
		int size2 = index2.size();

		int midD = (int)Math.ceil((size1 + size2) / 2);
		Storage forward = new Storage(midD);
		Storage reverse = new Storage(midD);

		// Eat any matches at the beginning
		int i = 0;
		while (i < size1 && i < size2)
		{
			T item1 = index1.get(i);
			if (comparator.compare(item1, index2.get(i)) == 0)
			{
				if (listener != null)
					listener.resultFound(i, item1, DiffAction.MATCH);
				i++;
			}
			else
			{
				break;
			}
		}

		SubsetIndex<T> sub1 = new SubsetIndex<T>(index1, i);
		SubsetIndex<T> sub2 = new SubsetIndex<T>(index2, i);

		int editDist = largestCommonSequence(sub1, sub2, comparator, maxDistance, listener, forward, reverse);

		return editDist;
	}

	protected DifferenceEngine()
	{}

	private <T> int largestCommonSequence(SubsetIndex<T> index1, SubsetIndex<T> index2, Comparator<T> comparator, int maxDistance,
			DiffListener<T> listener, Storage forward, Storage reverse)
	{
		int editDist;

		int size1 = index1.size();
		int size2 = index2.size();

		if (size1 == 0)
		{
			if (listener != null)
				for (int i = 0; i < size2; i++)
					listener.resultFound(i, index2.get(i), DiffAction.INSERT);
			editDist = size2;
		}
		else if (size2 == 0)
		{
			if (listener != null)
				for (int i = 0; i < size1; i++)
					listener.resultFound(i, index1.get(i), DiffAction.DELETE);
			editDist = size1;
		}
		else
		{
			Snake middleSnake = findMiddleSnake(index1, index2, comparator, maxDistance, forward, reverse);
			editDist = middleSnake.getD();
			if (editDist > 1)
			{
				SubsetIndex<T> sub1 = new SubsetIndex<T>(index1, 0, middleSnake.getX());
				SubsetIndex<T> sub2 = new SubsetIndex<T>(index2, 0, middleSnake.getY());
				largestCommonSequence(sub1, sub2, comparator, maxDistance, listener, forward, reverse);

				if (listener != null)
					for (int i = middleSnake.getX(); i <= middleSnake.getU() - 1; i++)
						listener.resultFound(i, index1.get(i), DiffAction.MATCH);

				sub1 = new SubsetIndex<T>(index1, middleSnake.getU(), size1 - middleSnake.getU());
				sub2 = new SubsetIndex<T>(index2, middleSnake.getV(), size2 - middleSnake.getV());
				largestCommonSequence(sub1, sub2, comparator, maxDistance, listener, forward, reverse);
			}
			else if (size1 < size2)
			{
				if (listener != null)
					for (int i = 0; i < size1; i++)
						listener.resultFound(i, index1.get(i), DiffAction.DELETE);
			}
			else
			{
				if (listener != null)
					for (int i = 0; i < size2; i++)
						listener.resultFound(i, index2.get(i), DiffAction.INSERT);
			}
		}

		return editDist;
	}

	private <T> Snake findMiddleSnake(Index<T> index1, Index<T> index2, Comparator<T> comparator, int maxDistance, Storage forward,
			Storage reverse)
	{
		Snake snake = null;

		int delta = index1.size() - index2.size();
		boolean deltaEven = (delta % 2 == 0);

		int size1 = index1.size();
		int size2 = index2.size();
		int midD = (int)Math.ceil((size1 + size2) / 2);

		forward.put(1, 0);
		reverse.put(delta - 1, size1);
		int x, y, u, v;
		for (int d = 0; d <= midD; d++)
		{
			for (int k = -d; k <= d; k += 2)
			{
				if (k == -d || (k != d && forward.get(k - 1) < forward.get(k + 1)))
					x = forward.get(k + 1);
				else
					x = forward.get(k - 1) + 1;
				y = x - k;
				while (x < size1 && y < size2 && comparator.compare(index1.get(x), index2.get(y)) == 0)
				{
					x++;
					y++;
				}
				forward.put(k, x);
				if (!deltaEven && k >= delta - (d - 1) && k <= delta + (d - 1))
				{
					u = reverse.get(k);
					if (x >= u)
					{
						v = u - k;
						snake = new Snake(2 * d - 1, u, v, x, y);
						break;
					}
				}
			}
			if (snake == null)
			{
				for (int k = -d; k <= d; k += 2)
				{
					int reverseK = k + delta;
					if (k == d || (k != -d && reverse.get(reverseK - 1) < reverse.get(reverseK + 1)))
						u = reverse.get(reverseK - 1);
					else
						u = reverse.get(reverseK + 1) - 1;
					v = u - reverseK;
					while (u > 0 && v > 0 && comparator.compare(index1.get(u - 1), index2.get(v - 1)) == 0)
					{
						u--;
						v--;
					}
					reverse.put(reverseK, u);
					if (deltaEven && reverseK >= -d && reverseK <= d)
					{
						x = forward.get(reverseK);
						if (u <= x)
						{
							y = x - (k + delta);
							snake = new Snake(2 * d, u, v, x, y);
							break;
						}
					}
				}
			}
			if (snake != null)
				break;
		}

		return snake;
	}

	/**
	 * An array of int with indices from [-range..range]
	 */
	private class Storage
	{
		private int[] mArray;
		private int mRange;

		public Storage(int range)
		{
			mRange = range;
			mArray = new int[2 * range + 1];
			reset();
		}

		public void reset()
		{
			for (int i = 0; i < mArray.length; i++)
				mArray[i] = -1;
		}

		public int get(int index)
		{
			return mArray[mRange + index];
		}

		public void put(int index, int value)
		{
			mArray[mRange + index] = value;
		}
	}

	private class Snake
	{
		private int mD, mX, mY, mU, mV;

		public Snake(int d, int x, int y, int u, int v)
		{
			mD = d;
			mX = x;
			mY = y;
			mU = u;
			mV = v;
		}

		public int getD()
		{
			return mD;
		}

		public int getX()
		{
			return mX;
		}

		public int getY()
		{
			return mY;
		}

		public int getU()
		{
			return mU;
		}

		public int getV()
		{
			return mV;
		}
	}

	private class SubsetIndex<T> implements Index<T>
	{
		private Index<T> mIndex;
		private int mOffset;
		private int mLength;

		public SubsetIndex(Index<T> index, int offset)
		{
			this(index, offset, index.size() - offset);
		}

		public SubsetIndex(Index<T> index, int offset, int length)
		{
			if (index == null)
				throw new IllegalArgumentException("Index canont be null");
			if (offset < 0)
				throw new IllegalArgumentException("Offset must be >= 0");
			if (length < 0)
				throw new IllegalArgumentException("Length must be >= 0");
			if (length > index.size() - offset)
				throw new IllegalArgumentException("Length must be <= index.size() - offset");
			mIndex = index;
			mLength = length;
			mOffset = offset;
		}

		@Override
		public T get(int index)
		{
			return mIndex.get(index + mOffset);
		}

		@Override
		public int size()
		{
			return mLength;
		}
	}
}
