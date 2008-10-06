package com.rjcass.hashiokakero;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BoardReader
{
	public BoardReader()
	{}

	public Board create(String filename)
	{
		Board board = null;

		BufferedReader r = null;
		try
		{
			r = new BufferedReader(new FileReader(filename));

			try
			{
				String sizeLine = r.readLine().toLowerCase().trim();
				String[] dimensions = sizeLine.split("x");
				if (dimensions.length != 2)
					throw new HashiException("Expected line 1 to be in the format nxn: " + sizeLine);
				int rows = Integer.valueOf(dimensions[0].trim());
				int cols = Integer.valueOf(dimensions[1].trim());

				board = new Board(rows, cols);

				for (int row = 0; row < rows; row++)
				{
					String line = r.readLine();
					for (int col = 0; col < cols; col++)
					{
						if (line.charAt(col) != '-')
						{
							int maxBridges = Integer.valueOf(Character.toString(line.charAt(col)));
							board.addIsland(row, col, maxBridges);
						}
					}
				}
			}
			catch (IOException e)
			{
				throw new HashiException("Error reading file:" + e.getLocalizedMessage());
			}
		}
		catch (FileNotFoundException e)
		{
			throw new HashiException("Cannot read file " + filename);
		}
		finally
		{
			if (r != null)
			{
				try
				{
					r.close();
				}
				catch (IOException e)
				{}
			}
		}

		return board;
	}
}
