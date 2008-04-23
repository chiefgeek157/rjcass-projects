package com.rjcass.service;

public abstract class BusinessServiceManager
{
	private static BusinessServiceManager sDelegate = null;

	public static BusinessService getService(String ifName)
	{
		if (sDelegate == null)
			throw new IllegalStateException("Delegate has not been set");
		return sDelegate.doGetBusinessService(ifName);
	}

	public static BusinessService getService(
			Class<? extends BusinessService> iface)
	{
		if (sDelegate == null)
			throw new IllegalStateException("Delegate has not been set");
		return sDelegate.doGetBusinessService(iface);
	}

	public BusinessServiceManager()
	{}

	protected final void setDelegate(BusinessServiceManager delegate)
	{
		if (sDelegate != null)
			throw new IllegalStateException("Attempt to create second instance");
		sDelegate = delegate;
	}

	protected abstract BusinessService doGetBusinessService(String name);

	protected abstract BusinessService doGetBusinessService(
			Class<? extends BusinessService> iface);
}
