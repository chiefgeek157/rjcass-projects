package com.rjcass.hashiokakero;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class HashiMain
{
	public static void main(String[] args)
	{
		new HashiMain().go(args[0]);
	}

	public HashiMain()
	{}

	public void go(String filename)
	{
		BoardReader reader = new BoardReader();
		Board board = reader.create(filename);

		board.dump(new PrintWriter(new OutputStreamWriter(System.out)));

		board.solve();

		board.dump(new PrintWriter(new OutputStreamWriter(System.out)));
	}

}
