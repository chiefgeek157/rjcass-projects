package com.rjcass.hashiokakero;

public abstract class Occupant
{
	private boolean mCommitted;
	
	public Occupant()
	{}

	public boolean isCommitted()
	{
		return mCommitted;
	}
	
	protected void doCommit()
	{
		mCommitted = true;
	}
}
