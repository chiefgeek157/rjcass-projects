package com.rjcass.diff.index;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rjcass.diff.Index;

public class MemoryFileIndex implements Index<String>
{
	private Map<Integer, String> mLines;

	public MemoryFileIndex(String filename) throws IOException
	{
		mLines = new HashMap<Integer, String>();

		BufferedReader r = new BufferedReader(new FileReader(filename));
		String line = r.readLine();
		int lineNum = 0;
		while (line != null)
		{
			mLines.put(lineNum, line);
			lineNum++;
			line = r.readLine();
		}
	}

	@Override
	public String get(int index)
	{
		return mLines.get(index);
	}

	@Override
	public int size()
	{
		return mLines.size();
	}
}
