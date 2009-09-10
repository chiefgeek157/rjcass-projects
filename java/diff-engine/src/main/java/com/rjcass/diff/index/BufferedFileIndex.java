package com.rjcass.diff.index;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

import com.rjcass.diff.DiffException;
import com.rjcass.diff.Index;

public class BufferedFileIndex implements Index<String>
{
	private static Log sLog = LogFactory.getLog(BufferedFileIndex.class);

	private BufferedReader mReader;
	private JCS mJCS;
	private int mNumLines;

	public BufferedFileIndex(String filename) throws IOException
	{
		try
		{
			// String cacheName = String.valueOf(hashCode()) +
			// String.valueOf(filename.hashCode());
			String cacheName = "cache1";
			sLog.debug("Loading '" + filename + "' into cache file '" + cacheName + "'");

			mReader = new BufferedReader(new FileReader(filename));
			mJCS = JCS.getInstance(cacheName);

			String line = mReader.readLine();
			while (line != null)
			{
				mJCS.put(new Integer(mNumLines), line);
				// sLog.debug("Stats: " + mJCS.getStats());
				mNumLines++;
				line = mReader.readLine();
			}
			sLog.debug("Added " + mNumLines + " lines to cache");
		}
		catch (CacheException e)
		{
			throw new DiffException(e);
		}
	}

	@Override
	public String get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size()
	{
		return mNumLines;
	}

	public void dispose()
	{
		try
		{
			mJCS.clear();
			mJCS.dispose();
		}
		catch (CacheException e)
		{
			throw new DiffException(e);
		}
	}
}
