package com.rjcass.crypto.tripledes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.BitSet;

public class TripleDES
{
	public static final int			KEY_GROUP_SIZE_BYTES	= 24;
	public static final int			KEY_GROUP_KEY_COUNT		= 3;

	public static final int			KEY_SIZE_BYTES			= 8;
	public static final int			KEY_SIZE_BITS			= 64;
	public static final int			PERMUTED_KEY_SIZE_BITS	= 28;

	public static final int			BLOCK_SIZE_BYTES		= 8;
	public static final int			BLOCK_SIZE_BITS			= 64;
	public static final int			SPLIT_BLOCK_SIZE_BITS	= 32;

	public static final int			NUM_PERMUTED_KEYS		= 16;

	public static final int			CIPHER_SIZE_BITS		= 48;

	public static final int			CIPHER_BLOCK_SIZE_BITS	= 6;
	public static final int			NUM_CIPHER_BLOCKS		= 8;

	public static final int			S_BLOCK_SIZE_BITS		= 4;

	private static final int[]		INITIAL_PERMUTATION		= new int[] { 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11,
			3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7, 56, 48, 40, 32, 24, 16, 8, 0, 58, 50, 42, 34, 26, 18,
			10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6 };

	private static final int[]		INVERSE_PERMUTATION		= new int[] { 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62,
			30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18,
			58, 26, 33, 1, 41, 9, 49, 17, 57, 25, 32, 0, 40, 8, 48, 16, 56, 24 };

	private static final int[]		PERMUTED_CHOICE_1		= new int[] { 56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 41, 33, 25, 17, 9,
			1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 60, 52,
			44, 36, 28, 20, 12, 4, 27, 19, 11, 3			};

	private static final int[]		PERMUTED_CHOICE_2		= new int[] { 13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9, 22, 18, 11, 3,
			25, 7, 15, 6, 26, 19, 12, 1, 40, 51, 30, 36, 46, 54, 29, 39, 50, 44, 32, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35,
			28, 31											};

	private static final int[]		PERMUTED_KEY_LEFT_SHIFT	= new int[] { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };

	private static final int[]		E_PERMUTATION			= new int[] { 31, 0, 1, 2, 3, 4, 3, 4, 5, 6, 7, 8, 7, 8, 9, 10, 11, 12,
			11, 12, 13, 14, 15, 16, 15, 16, 17, 18, 19, 20, 19, 20, 21, 22, 23, 24, 23, 24, 25, 26, 27, 28, 27, 28, 29, 30, 31, 0 };

	private static final int[]		P_PERMUTATION			= new int[] { 15, 6, 19, 20, 28, 11, 27, 16, 0, 14, 22, 25, 4, 17, 30,
			9, 1, 7, 23, 13, 31, 26, 2, 8, 18, 12, 29, 5, 21, 10, 3, 24 };

	private static final byte[][][]	S_TABLES				= new byte[][][] {
			{ { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 }, { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
			{ 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 }, { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } },
			{ { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 }, { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
			{ 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 }, { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } },
			{ { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 }, { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
			{ 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 }, { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } },
			{ { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 }, { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
			{ 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 }, { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } },
			{ { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 }, { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
			{ 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 }, { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } },
			{ { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 }, { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
			{ 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 }, { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } },
			{ { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 }, { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
			{ 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 }, { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } },
			{ { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 }, { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
			{ 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 }, { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } } };

	public void simpleTest(InputStream is, byte[] keyGroup, OutputStream os) throws IOException
	{
		BitSet[] keys = extractKeys(keyGroup);

		BlockGenerator bg = new BlockGenerator(is);
		while (bg.hasMoreBlocks())
		{
			BitSet block = bg.nextBlock();
			BitSet o1 = encryptBlock(keys[0], block);
			BitSet o2 = decryptBlock(keys[0], o1);
			BlockGenerator.writeBlock(o2, BLOCK_SIZE_BITS, os);
		}
	}

	public void encrypt(InputStream is, byte[] keyGroup, OutputStream os) throws IOException
	{
		if (keyGroup == null)
			throw new IllegalArgumentException("Key group cannot be null");
		if (keyGroup.length != KEY_GROUP_SIZE_BYTES)
			throw new IllegalArgumentException("Key group must be " + KEY_GROUP_SIZE_BYTES + " bytes");
		if (is == null)
			throw new IllegalArgumentException("Input stream cannot be null");
		if (os == null)
			throw new IllegalArgumentException("Output stream cannot be null");

		// System.out.println("Key Group: " + BitPrinter.toString(keyGroup));
		BitSet[] keys = extractKeys(keyGroup);

		BlockGenerator bg = new BlockGenerator(is);
		while (bg.hasMoreBlocks())
		{
			BitSet block = bg.nextBlock();
			BitSet o1 = encryptBlock(keys[0], block);
			BitSet o2 = decryptBlock(keys[1], o1);
			BitSet o3 = encryptBlock(keys[2], o2);
			BlockGenerator.writeBlock(o3, BLOCK_SIZE_BITS, os);
		}
	}

	public void decrypt(InputStream is, byte[] keyGroup, OutputStream os) throws IOException
	{
		if (keyGroup == null)
			throw new IllegalArgumentException("Key group cannot be null");
		if (keyGroup.length != KEY_GROUP_SIZE_BYTES)
			throw new IllegalArgumentException("Key group must be " + KEY_GROUP_SIZE_BYTES + " bytes");
		if (is == null)
			throw new IllegalArgumentException("Input stream cannot be null");
		if (os == null)
			throw new IllegalArgumentException("Output stream cannot be null");

		BitSet[] keys = extractKeys(keyGroup);

		BlockGenerator bg = new BlockGenerator(is);
		while (bg.hasMoreBlocks())
		{
			BitSet block = bg.nextBlock();
			BitSet o1 = decryptBlock(keys[2], block);
			BitSet o2 = encryptBlock(keys[1], o1);
			BitSet o3 = decryptBlock(keys[0], o2);
			BlockGenerator.writeBlock(o3, BLOCK_SIZE_BITS, os);
		}
	}

	private BitSet[] extractKeys(byte[] keyGroup)
	{
		BitSet[] keys = new BitSet[KEY_GROUP_KEY_COUNT];
		for (int i = 0; i < KEY_GROUP_KEY_COUNT; i++)
		{
			keys[i] = new BitSet(KEY_SIZE_BITS);
			for (int j = 0; j < KEY_SIZE_BYTES; j++)
			{
				byte b = keyGroup[KEY_SIZE_BYTES * i + j];
				for (int k = 0; k < Byte.SIZE; k++)
				{
					keys[i].set(Byte.SIZE * j + k, (b & 0x1) == 0x1);
					b >>>= 1;
				}
			}
			// System.out.println("Key[" + i + "]: " +
			// BitPrinter.toString(keys[i], KEY_SIZE_BITS));
		}
		return keys;
	}

	private BitSet encryptBlock(BitSet key, BitSet block)
	{
		// System.out.println("Key  : " + BitPrinter.toString(key,
		// KEY_SIZE_BITS));
		// System.out.println("Block: " + BitPrinter.toString(block,
		// BLOCK_SIZE_BITS));

		BitSet[] permutedKeys = getPermutedKeys(key);

		BitSet ip = permute(block, INITIAL_PERMUTATION, BLOCK_SIZE_BITS);
		// System.out.println("   IP    : " + BitPrinter.toString(ip,
		// BLOCK_SIZE_BITS));

		BitSet[] lr0 = split(ip, BLOCK_SIZE_BITS, SPLIT_BLOCK_SIZE_BITS);

		BitSet[] lr1 = new BitSet[2];

		for (int i = 0; i < NUM_PERMUTED_KEYS; i++)
		{
			lr1[0] = (BitSet)lr0[1].clone();
			lr1[1] = (BitSet)lr0[0].clone();
			// System.out.println("   Perm " + i + " LR0[0]: " +
			// BitPrinter.toString(lr0[0], SPLIT_BLOCK_SIZE_BITS));
			// System.out.println("   Perm " + i + " LR0[1]: " +
			// BitPrinter.toString(lr0[1], SPLIT_BLOCK_SIZE_BITS));
			// System.out.println("   Perm " + i + " LR1[0]: " +
			// BitPrinter.toString(lr1[0], SPLIT_BLOCK_SIZE_BITS));
			// System.out.println("   Perm " + i + " LR1[1]: " +
			// BitPrinter.toString(lr1[1], SPLIT_BLOCK_SIZE_BITS));

			BitSet f = computeCipher(lr0[1], permutedKeys[i]);
			// System.out.println("   F            : " + BitPrinter.toString(f,
			// SPLIT_BLOCK_SIZE_BITS));
			lr1[1].xor(f);
			// System.out.println("   LR1[1] XOR F : " +
			// BitPrinter.toString(lr1[1], SPLIT_BLOCK_SIZE_BITS));

			BitSet temp0 = lr0[0];
			BitSet temp1 = lr0[1];
			lr0[0] = lr1[0];
			lr0[1] = lr1[1];
			lr1[0] = temp0;
			lr1[1] = temp1;
		}

		// System.out.println("   LR0[0]   : " + BitPrinter.toString(lr0[0],
		// SPLIT_BLOCK_SIZE_BITS));
		// System.out.println("   LR0[1]   : " + BitPrinter.toString(lr0[1],
		// SPLIT_BLOCK_SIZE_BITS));
		// System.out.println("   LR1[0]   : " + BitPrinter.toString(lr1[0],
		// SPLIT_BLOCK_SIZE_BITS));
		// System.out.println("   LR1[1]   : " + BitPrinter.toString(lr1[1],
		// SPLIT_BLOCK_SIZE_BITS));

		lr1[0] = lr0[1];
		lr1[1] = lr0[0];

		// System.out.println("   Swapped L: " + BitPrinter.toString(lr1[0],
		// SPLIT_BLOCK_SIZE_BITS));
		// System.out.println("   Swapped R: " + BitPrinter.toString(lr1[1],
		// SPLIT_BLOCK_SIZE_BITS));

		join(lr1, SPLIT_BLOCK_SIZE_BITS, ip);
		// System.out.println("   Joined   : " + BitPrinter.toString(ip,
		// BLOCK_SIZE_BITS));

		BitSet iip = permute(ip, INVERSE_PERMUTATION, BLOCK_SIZE_BITS);

		// System.out.println("   IIP      : " + BitPrinter.toString(iip,
		// BLOCK_SIZE_BITS));

		return iip;
	}

	private BitSet decryptBlock(BitSet key, BitSet block)
	{
		// System.out.println("Key  : " + BitPrinter.toString(key,
		// KEY_SIZE_BITS));
		// System.out.println("Block: " + BitPrinter.toString(block,
		// BLOCK_SIZE_BITS));

		BitSet[] permutedKeys = getPermutedKeys(key);

		BitSet ip = permute(block, INITIAL_PERMUTATION, BLOCK_SIZE_BITS);
		// System.out.println("   IP    : " + BitPrinter.toString(ip,
		// BLOCK_SIZE_BITS));

		BitSet[] lr0 = split(ip, BLOCK_SIZE_BITS, SPLIT_BLOCK_SIZE_BITS);

		BitSet[] lr1 = new BitSet[2];

		for (int i = NUM_PERMUTED_KEYS - 1; i >= 0; i--)
		{
			lr1[0] = (BitSet)lr0[1].clone();
			lr1[1] = (BitSet)lr0[0].clone();
			// System.out.println("   Perm " + i + " LR0[0]: " +
			// BitPrinter.toString(lr0[0], SPLIT_BLOCK_SIZE_BITS));
			// System.out.println("   Perm " + i + " LR0[1]: " +
			// BitPrinter.toString(lr0[1], SPLIT_BLOCK_SIZE_BITS));
			// System.out.println("   Perm " + i + " LR1[0]: " +
			// BitPrinter.toString(lr1[0], SPLIT_BLOCK_SIZE_BITS));
			// System.out.println("   Perm " + i + " LR1[1]: " +
			// BitPrinter.toString(lr1[1], SPLIT_BLOCK_SIZE_BITS));

			BitSet f = computeCipher(lr0[1], permutedKeys[i]);
			// System.out.println("   F            : " + BitPrinter.toString(f,
			// SPLIT_BLOCK_SIZE_BITS));
			lr1[1].xor(f);
			// System.out.println("   LR1[1] XOR F : " +
			// BitPrinter.toString(lr1[1], SPLIT_BLOCK_SIZE_BITS));

			BitSet temp0 = lr0[0];
			BitSet temp1 = lr0[1];
			lr0[0] = lr1[0];
			lr0[1] = lr1[1];
			lr1[0] = temp0;
			lr1[1] = temp1;
		}

		// System.out.println("   LR0[0]   : " + BitPrinter.toString(lr0[0],
		// SPLIT_BLOCK_SIZE_BITS));
		// System.out.println("   LR0[1]   : " + BitPrinter.toString(lr0[1],
		// SPLIT_BLOCK_SIZE_BITS));
		// System.out.println("   LR1[0]   : " + BitPrinter.toString(lr1[0],
		// SPLIT_BLOCK_SIZE_BITS));
		// System.out.println("   LR1[1]   : " + BitPrinter.toString(lr1[1],
		// SPLIT_BLOCK_SIZE_BITS));

		lr1[0] = lr0[1];
		lr1[1] = lr0[0];

		// System.out.println("   Swapped L: " + BitPrinter.toString(lr1[0],
		// SPLIT_BLOCK_SIZE_BITS));
		// System.out.println("   Swapped R: " + BitPrinter.toString(lr1[1],
		// SPLIT_BLOCK_SIZE_BITS));

		join(lr1, SPLIT_BLOCK_SIZE_BITS, ip);
		// System.out.println("   Joined   : " + BitPrinter.toString(ip,
		// BLOCK_SIZE_BITS));

		BitSet iip = permute(ip, INVERSE_PERMUTATION, BLOCK_SIZE_BITS);

		// System.out.println("   IIP      : " + BitPrinter.toString(iip,
		// BLOCK_SIZE_BITS));

		return iip;
	}

	private BitSet computeCipher(BitSet cipherBlock, BitSet permutedKey)
	{
		// System.out.println("      cipher block    : " +
		// BitPrinter.toString(cipherBlock, SPLIT_BLOCK_SIZE_BITS));

		BitSet eBlock = permute(cipherBlock, E_PERMUTATION, CIPHER_SIZE_BITS);
		// System.out.println("      e block         : " +
		// BitPrinter.toString(eBlock, CIPHER_SIZE_BITS, 6));

		eBlock.xor(permutedKey);
		// System.out.println("      e xor key       : " +
		// BitPrinter.toString(eBlock, CIPHER_SIZE_BITS, 6));

		BitSet[] cipherBlocks = split(eBlock, CIPHER_SIZE_BITS, CIPHER_BLOCK_SIZE_BITS);

		BitSet[] sBlocks = new BitSet[NUM_CIPHER_BLOCKS];
		for (int i = 0; i < NUM_CIPHER_BLOCKS; i++)
		{
			// System.out.println("         cipher: " +
			// BitPrinter.toString(cipherBlocks[i], CIPHER_BLOCK_SIZE_BITS, 6));
			sBlocks[i] = computeS(cipherBlocks[i], S_TABLES[i]);
		}

		BitSet cipher = new BitSet(SPLIT_BLOCK_SIZE_BITS);
		join(sBlocks, 4, cipher);
		// System.out.println("      cipher : " + BitPrinter.toString(cipher,
		// SPLIT_BLOCK_SIZE_BITS));

		cipher = permute(cipher, P_PERMUTATION, SPLIT_BLOCK_SIZE_BITS);
		// System.out.println("      pcipher: " + BitPrinter.toString(cipher,
		// SPLIT_BLOCK_SIZE_BITS));

		return cipher;
	}

	private BitSet computeS(BitSet b, byte[][] table)
	{
		byte row = 0x0;
		if (b.get(0))
			row += 0x1;
		if (b.get(5))
			row += 0x2;

		byte col = 0x0;
		if (b.get(1))
			col += 0x1;
		if (b.get(2))
			col += 0x2;
		if (b.get(3))
			col += 0x4;
		if (b.get(4))
			col += 0x8;

		byte sByte = table[row][col];

		BitSet s = new BitSet(S_BLOCK_SIZE_BITS);
		s.set(0, (sByte & 0x1) == 0x1);
		s.set(1, (sByte & 0x2) == 0x2);
		s.set(2, (sByte & 0x4) == 0x4);
		s.set(3, (sByte & 0x8) == 0x8);
		// System.out.println("            R" + row + "C" + col + " byte " +
		// sByte + " s "
		// + BitPrinter.toString(s, S_BLOCK_SIZE_BITS, 4));

		return s;
	}

	private BitSet[] getPermutedKeys(BitSet key)
	{
		BitSet[] permutedKeys = new BitSet[NUM_PERMUTED_KEYS];

		BitSet pk = permute(key, PERMUTED_CHOICE_1, PERMUTED_KEY_SIZE_BITS * 2);

		BitSet[] cd0 = split(pk, PERMUTED_KEY_SIZE_BITS * 2, PERMUTED_KEY_SIZE_BITS);

		BitSet[] cd1 = new BitSet[2];
		cd1[0] = new BitSet(PERMUTED_KEY_SIZE_BITS);
		cd1[1] = new BitSet(PERMUTED_KEY_SIZE_BITS);

		for (int i = 0; i < NUM_PERMUTED_KEYS; i++)
		{
			leftShift(cd0[0], PERMUTED_KEY_LEFT_SHIFT[i], PERMUTED_KEY_SIZE_BITS, cd1[0]);
			leftShift(cd0[1], PERMUTED_KEY_LEFT_SHIFT[i], PERMUTED_KEY_SIZE_BITS, cd1[1]);

			join(cd1, PERMUTED_KEY_SIZE_BITS, pk);

			permutedKeys[i] = permute(pk, PERMUTED_CHOICE_2, CIPHER_SIZE_BITS);
			// System.out.println("K" + i + ": " +
			// BitPrinter.toString(permutedKeys[i], PERMUTED_KEY_SIZE_BITS));

			BitSet temp0 = cd0[0];
			BitSet temp1 = cd0[1];
			cd0[0] = cd1[0];
			cd0[1] = cd1[1];
			cd1[0] = temp0;
			cd1[1] = temp1;
		}

		return permutedKeys;
	}

	private BitSet permute(BitSet b, int[] permutation, int size)
	{
		BitSet p = new BitSet(size);
		for (int i = 0; i < size; i++)
		{
			p.set(i, b.get(permutation[i]));
		}
		return p;
	}

	private BitSet[] split(BitSet b, int size, int splitSize)
	{
		// System.out.println("Split b: " + BitPrinter.toString(b, size,
		// splitSize));
		int numSplits = size / splitSize;
		BitSet[] sets = new BitSet[numSplits];
		for (int i = 0; i < numSplits; i++)
		{
			sets[i] = b.get(i * splitSize, (i + 1) * splitSize);
			// System.out.println("Split " + i + ": " +
			// BitPrinter.toString(sets[i], splitSize));
		}
		return sets;
	}

	private void join(BitSet[] sets, int size, BitSet b)
	{
		for (int i = 0; i < sets.length; i++)
		{
			for (int j = 0; j < size; j++)
				b.set(size * i + j, sets[i].get(j));
		}
	}

	private BitSet leftShift(BitSet b, int shift, int size, BitSet b1)
	{
		for (int i = 0; i < size - shift; i++)
			b1.set(i, b.get(i + shift));
		for (int i = 0; i < shift; i++)
			b1.set(size - shift + i, b.get(i));
		return b1;
	}
}
