package com.rjcass.diff;

public interface DiffListener<T>
{
	enum DiffAction
	{
		MATCH, DELETE, INSERT
	};

	void resultFound(int line, T item, DiffAction diffAction);
}
