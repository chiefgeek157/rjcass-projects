package com.rjcass.diff;

public interface Index<T>
{
	/**
	 * Return the size of the index.
	 * 
	 * @return The size of the Index.
	 */
	int size();

	/**
	 * Return the item at the given position.
	 * 
	 * @param index
	 *            The position of an item.
	 * @return The item at the given position.
	 */
	T get(int pos);
}
