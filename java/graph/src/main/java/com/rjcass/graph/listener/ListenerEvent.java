package com.rjcass.graph.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rjcass.graph.GEntity;

public class ListenerEvent
{
	private ListenerEventType mType;
	private List<String> mNames;

	public ListenerEvent(ListenerEventType type, Object... objs)
	{
		mType = type;
		mNames = new ArrayList<String>();
		for (Object obj : objs)
		{
			if (obj == null)
				mNames.add("null");
			else if (obj instanceof GEntity)
				mNames.add(((GEntity)obj).getId());
			else
				mNames.add(obj.toString());
		}
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		if (obj != null && obj instanceof ListenerEvent)
		{
			ListenerEvent other = (ListenerEvent)obj;
			if (other.mType == mType && other.mNames.size() == mNames.size())
			{
				result = true;
				Iterator<String> thisIter = mNames.iterator();
				Iterator<String> otherIter = other.mNames.iterator();
				while (thisIter.hasNext())
				{
					if (!thisIter.next().equals(otherIter.next()))
					{
						result = false;
						break;
					}
				}
			}
		}
		return result;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder("Event[");
		builder.append(mType);
		for (String name : mNames)
		{
			builder.append(",\"").append(name).append("\"");
		}
		builder.append("]");
		return builder.toString();
	}
}
