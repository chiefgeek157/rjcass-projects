package com.rjcass.crypto.tripledes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.BitSet;

public class BlockGenerator
{
	private InputStream	mInput;
	private byte[]		mBuffer;
	private int			mNumRead;

	public static void writeBlock(BitSet bs, int size, OutputStream os) throws IOException
	{
		byte[] buf = new byte[(int)Math.ceil(((double)size) / Byte.SIZE)];
		int byteIndex = 0;
		int bitIndex = 0;
		byte mask = 0x1;
		byte b = 0x0;
		for (int i = 0; i < size; i++)
		{
			if (bs.get(i))
				b += mask;
			if (bitIndex == Byte.SIZE - 1)
			{
				buf[byteIndex] = b;
				bitIndex = 0;
				byteIndex++;
				mask = 0x1;
				b = 0x0;
			}
			else
			{
				bitIndex++;
				mask <<= 1;
			}
		}
		os.write(buf);
	}

	public BlockGenerator(InputStream input)
	{
		mInput = input;
		mBuffer = new byte[TripleDES.BLOCK_SIZE_BYTES];
	}

	public boolean hasMoreBlocks() throws IOException
	{
		mNumRead = mInput.read(mBuffer);
		return (mNumRead > 0);
	}

	public BitSet nextBlock() throws IOException
	{
		BitSet block = new BitSet(TripleDES.BLOCK_SIZE_BITS);
		for (int i = 0; i < mNumRead; i++)
		{
			byte b = mBuffer[i];
			// System.out.println("   Byte " + i + ": " +
			// BitPrinter.toString(b));
			for (int j = 0; j < Byte.SIZE; j++)
			{
				block.set(Byte.SIZE * i + j, (b & 0x1) == 0x1);
				b >>>= 1;
			}
			// System.out.println("   Block : " +
			// BitPrinter.toString(block));
		}
		return block;
	}
}
