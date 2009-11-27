package com.rjcass.crypto.tripledes;

import java.util.BitSet;

public class BitPrinter
{
	public static String toString(byte b)
	{
		return toString(new byte[] { b });
	}

	public static String toString(byte[] bb)
	{
		return toString(bb, Byte.SIZE);
	}

	public static String toString(byte[] bb, int groupSize)
	{
		return toString(bb, 0, bb.length, groupSize);
	}

	public static String toString(byte[] bb, int start, int end)
	{
		return toString(bb, start, end, Byte.SIZE);
	}

	public static String toString(byte[] bb, int start, int end, int groupSize)
	{
		int length = (end - start) * Byte.SIZE;
		StringBuilder sb = new StringBuilder(length + length / groupSize - 1);
		int bitCount = 0;
		for (int i = start; i < end; i++)
		{
			byte mask = 0x1;
			for (int j = 0; j < Byte.SIZE; j++)
			{
				if ((bb[i] & mask) == mask)
					sb.append("1");
				else
					sb.append("0");
				mask <<= 1;
				bitCount++;
				if (bitCount == groupSize && !(j == Byte.SIZE - 1 && i == end - 1))
				{
					sb.append(" ");
					bitCount = 0;
				}
			}
		}
		return sb.toString();
	}

	public static String toString(BitSet b)
	{
		return toString(b, b.length());
	}

	public static String toString(BitSet b, int size)
	{
		return toString(b, size, Byte.SIZE);
	}

	public static String toString(BitSet b, int size, int groupSize)
	{
		StringBuilder sb = new StringBuilder(size + size / groupSize - 1);
		int bitCount = 0;
		for (int i = 0; i < size; i++)
		{
			if (b.get(i))
				sb.append("1");
			else
				sb.append("0");
			bitCount++;
			if (bitCount == groupSize && i != b.size() - 1)
			{
				sb.append(" ");
				bitCount = 0;
			}
		}
		return sb.toString();
	}
}
