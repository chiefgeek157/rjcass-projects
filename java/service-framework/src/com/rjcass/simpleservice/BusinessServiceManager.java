package com.rjcass.simpleservice;

public abstract class BusinessServiceManager
{
	private static BusinessServiceManager sDelegate;

	public BusinessService getBusinessService(String name)
	{
		if (sDelegate == null)
			throw new IllegalStateException("Not initialized");
		return sDelegate.doGetBusinessService(name);
	}

	public static ServiceRequest createServiceRequest()
	{
		if (sDelegate == null)
			throw new IllegalStateException("Not initialized");
		return sDelegate.doCreateServiceRequest();
	}

	public static ServiceReply createServiceReply()
	{

		if (sDelegate == null)
			throw new IllegalStateException("Not initialized");
		return sDelegate.doCreateServiceReply();
	}

	public BusinessServiceManager()
	{}

	protected final void setDelegate(BusinessServiceManager delegate)
	{
		if (sDelegate != null)
			throw new IllegalArgumentException("Cannot create second instance");
		sDelegate = delegate;
	}

	protected abstract BusinessService doGetBusinessService(String name);

	protected abstract ServiceRequest doCreateServiceRequest();

	protected abstract ServiceReply doCreateServiceReply();
}
