package com.rjcass.xbml.m2analyzer.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.rjcass.xbml.m2analyzer.M2AnalyzerException;

public class ZipScanner implements RepositoryScanner
{
	// private static Log sLog = LogFactory.getLog(ZipScanner.class);

	private SnippetFactory mFactory;
	private ZipFile mFile;
	private int mCount;

	ZipScanner(File file, SnippetFactory factory)
	{
		mFactory = factory;
		try
		{
			mFile = new ZipFile(file);
		}
		catch (IOException e)
		{
			throw new M2AnalyzerException(e);
		}
	}

	@Override
	public int getPercentComplete()
	{
		int percent = 0;
		if (mFile.size() > 0)
			percent = 100 * mCount / mFile.size();
		return percent;
	}

	@Override
	public Iterator<Snippet> iterator()
	{
		return new ZipFileSnippetIterator(mFile);
	}

	private class ZipFileSnippetIterator implements Iterator<Snippet>
	{
		private ZipFile mZipFile;
		private Enumeration<? extends ZipEntry> mEntries;
		private ZipEntry mNextEntry;

		public ZipFileSnippetIterator(ZipFile file)
		{
			mZipFile = file;
			mEntries = file.entries();
			updateNextEntry();
		}

		@Override
		public boolean hasNext()
		{
			return mNextEntry != null;
		}

		@Override
		public Snippet next()
		{
			Snippet snippet = null;
			try
			{
				InputStream is = mZipFile.getInputStream(mNextEntry);
				snippet = mFactory.createSnippet(mNextEntry.getName(), is);
				updateNextEntry();
			}
			catch (IOException e)
			{
				throw new M2AnalyzerException(e);
			}
			return snippet;
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}

		private void updateNextEntry()
		{
			mNextEntry = null;
			while (mNextEntry == null && mEntries.hasMoreElements())
			{
				ZipEntry entry = mEntries.nextElement();
				mCount++;
				if (entry.getName().endsWith(".xml"))
				{
					mNextEntry = entry;
					break;
				}
			}
		}
	}
}
