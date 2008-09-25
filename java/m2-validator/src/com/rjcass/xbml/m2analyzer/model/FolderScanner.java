package com.rjcass.xbml.m2analyzer.model;

import java.io.File;
import java.io.FileFilter;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FolderScanner implements RepositoryScanner, Iterator<Snippet>
{
	private static Log sLog = LogFactory.getLog(FolderScanner.class);

	private SnippetFactory mFactory;
	private Deque<File> mWorkList;
	private int mCount;
	private int mSize;

	FolderScanner(File file, SnippetFactory factory)
	{
		mFactory = factory;
		if (!file.isDirectory()) throw new IllegalArgumentException("Path must be a folder: " + file);
		if (!file.canRead()) throw new IllegalArgumentException("Path must be readable: " + file);
		mWorkList = new LinkedList<File>();
		addDirectoryFiles(file);
		mSize = mWorkList.size();
	}

	@Override
	public int getPercentComplete()
	{
		int percent = 0;
		if (mSize>0)
			percent = mCount / mSize;
		return percent;
	}

	@Override
	public Iterator<Snippet> iterator()
	{
		return this;
	}

	@Override
	public boolean hasNext()
	{
		return !mWorkList.isEmpty();
	}

	@Override
	public Snippet next()
	{
		mCount++;
		File file = mWorkList.removeFirst();
		Snippet snippet = mFactory.createSnippet(file);
		return snippet;
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	private void addDirectoryFiles(File dir)
	{
		// sLog.debug("Scanning directory: " + dir);
		File[] files = dir.listFiles(new FileFilter()
		{
			public boolean accept(File file)
			{
				if (file.isDirectory() || file.getName().endsWith(".xml"))
					return true;
				else
					return false;
			}
		});
		for (int i = 0; i < files.length; i++)
		{
			if (files[i].isDirectory())
			{
				addDirectoryFiles(files[i]);
			}
			else
			{
				sLog.debug("Adding File: " + files[i]);
				mWorkList.addFirst(files[i]);
			}
		}
	}
}
