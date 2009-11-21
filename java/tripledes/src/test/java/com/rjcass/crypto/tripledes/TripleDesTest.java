package com.rjcass.crypto.tripledes;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TripleDesTest
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
	public void testRoundTrip() throws IOException
	{
		int inputSize = (int)Math.round(Math.random() * 10000);

		byte[] keyGroup = new byte[24];
		byte[] input = new byte[inputSize];

		for (int i = 0; i < 24; i++)
			keyGroup[i] = (byte)Math.round(Math.random() * 256);

		System.out.println("Input size: " + inputSize);
		for (int i = 0; i < inputSize; i++)
			input[i] = (byte)Math.round(Math.random() * 256);

		TripleDES tdes = new TripleDES();
		ByteArrayInputStream is = new ByteArrayInputStream(input);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		tdes.encrypt(is, keyGroup, os);
		byte[] encrypted = os.toByteArray();
		is = new ByteArrayInputStream(encrypted);
		os = new ByteArrayOutputStream();
		tdes.decrypt(is, keyGroup, os);
		byte[] decrypted = os.toByteArray();
		for (int i = 0; i < input.length; i++)
			if (input[i] != decrypted[i])
				fail("Arrays differ");
	}
}
