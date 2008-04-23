package com.rjcass.logging;

public abstract class LogManager
{
	private static LogManager sDelegate;

	public static Log getLog(String name)
	{
		return new LogManager.LogHolder(name);
	}

	public static Log getLog(Class<?> cls)
	{
		return new LogHolder(cls);
	}

	static Log getLogDelegate(String name)
	{
		if (sDelegate == null)
			throw new IllegalStateException("Delegate has not been set");
		return sDelegate.doGetLogDelegate(name);
	}

	static Log getLogDelegate(Class<?> cls)
	{
		if (sDelegate == null)
			throw new IllegalStateException("Delegate has not been set");
		return sDelegate.doGetLogDelegate(cls);
	}

	protected LogManager()
	{}

	protected final void setDelegate(LogManager delegate)
	{
		if (sDelegate != null)
			throw new IllegalStateException("Attempt to create second instance");
		sDelegate = delegate;
	}

	protected abstract Log doGetLogDelegate(String name);

	protected abstract Log doGetLogDelegate(Class<?> cls);

	private static class LogHolder implements Log
	{
		private String mName;
		private Class<?> mClass;
		private Log mDelegate;

		public LogHolder(String name)
		{
			mName = name;
		}

		public LogHolder(Class<?> cls)
		{
			mClass = cls;
		}

		@Override
		public void debug(Object obj)
		{
			ensureDelegate();
			mDelegate.debug(obj);
		}

		private void ensureDelegate()
		{
			if (mDelegate == null)
			{
				if (mClass != null)
				{
					mDelegate = LogManager.getLogDelegate(mClass);
					mClass = null;
				}
				else if (mName != null)
				{
					mDelegate = LogManager.getLogDelegate(mName);
					mName = null;
				}
				else
					throw new IllegalStateException("Not initialized correclty");
			}
		}
	}
}