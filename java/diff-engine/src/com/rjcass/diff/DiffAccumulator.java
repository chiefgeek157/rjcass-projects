package com.rjcass.diff;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiffAccumulator<T> implements DiffListener<T>
{
	private List<DiffResult> mDiffs;

	public DiffAccumulator()
	{
		mDiffs = new ArrayList<DiffResult>();
	}

	@Override
	public void resultFound(int line, T item, DiffAction action)
	{
		mDiffs.add(new DiffResult(line, item, action));
	}

	public List<DiffResult> getResults()
	{
		return Collections.unmodifiableList(mDiffs);
	}

	public void dumpResults(OutputStream os) throws IOException
	{
		dumpResults(os, false);
	}

	public void dumpResults(OutputStream os, boolean includeMatches) throws IOException
	{
		dumpResults(new OutputStreamWriter(os), includeMatches);
	}

	public void dumpResults(Writer w) throws IOException
	{
		dumpResults(w, false);
	}

	public void dumpResults(Writer w, boolean includeMatches) throws IOException
	{
		PrintWriter pw = new PrintWriter(w);
		for (DiffResult result : mDiffs)
		{
			if (includeMatches || result.getAction() != DiffAction.MATCH)
				pw.println("Line " + result.getLine() + ": " + result.getAction() + " " + result.getItem());
		}
		w.flush();
	}

	public class DiffResult
	{
		private int mLine;
		private T mItem;
		private DiffAction mAction;

		public DiffResult(int line, T item, DiffAction action)
		{
			mLine = line;
			mItem = item;
			mAction = action;
		}

		public int getLine()
		{
			return mLine;
		}

		public T getItem()
		{
			return mItem;
		}

		public DiffAction getAction()
		{
			return mAction;
		}
	}
}
