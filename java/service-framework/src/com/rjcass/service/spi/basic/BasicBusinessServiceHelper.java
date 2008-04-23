package com.rjcass.service.spi.basic;

import com.rjcass.service.BusinessServiceManager;
import com.rjcass.service.spi.BusinessServiceHelper;
import com.rjcass.service.spi.ServiceReply;
import com.rjcass.service.spi.ServiceRequest;

public class BasicBusinessServiceHelper implements BusinessServiceHelper
{
	private BusinessServiceManager mBusinessServiceManager;
	private ServiceRequestFactory mServiceRequestFactory;
	private ServiceReplyFactory mServiceReplyFactory;

	public BasicBusinessServiceHelper(BusinessServiceManager serviceManager,ServiceRequestFactory requestFactory,ServiceReplyFactory replyFactory)
	{
		mBusinessServiceManager = serviceManager;
		mServiceRequestFactory = requestFactory;
		mServiceReplyFactory = replyFactory;
	}
	
	@Override
	public BusinessServiceManager getBusinessServiceManager()
	{
		return mBusinessServiceManager;
	}

	@Override
	public ServiceRequest createServiceRequest()
	{
		return mServiceRequestFactory.createServiceRequest();
	}

	@Override
	public ServiceReply createServiceReply()
	{
		return mServiceReplyFactory.createServiceReply();
	}
}
