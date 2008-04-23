package com.rjcass.service.spi;

import com.rjcass.service.BusinessService;
import com.rjcass.service.BusinessServiceManager;

public abstract class AbstractBusinessService implements BusinessService,
		BusinessServiceHelperAware
{
	private BusinessServiceHelper mHelper;

	public void setBusinessServiceHelper(BusinessServiceHelper helper)
	{
		mHelper = helper;
	}

	/**
	 * Convenience method to return the BusinessServiceManager from the
	 * BusinessServiceHelper.
	 * 
	 * @return BusinessServiceManager
	 */
	protected BusinessServiceManager getBusinessServiceManager()
	{
		return mHelper.getBusinessServiceManager();
	}

	/**
	 * Convenience method to return a ServiceRequest from the
	 * BusinessServiceHelper.
	 * 
	 * @return ServiceRequest
	 */
	protected ServiceRequest createServiceRequest()
	{
		return mHelper.createServiceRequest();
	}

	/**
	 * Convenience method to return a ServiceReply from the
	 * BusinessServiceHelper.
	 * 
	 * @return ServiceReply
	 */
	protected ServiceReply createServiceReply()
	{
		return mHelper.createServiceReply();
	}
}
