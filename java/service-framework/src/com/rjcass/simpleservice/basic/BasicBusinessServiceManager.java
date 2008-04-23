package com.rjcass.simpleservice.basic;

import java.util.HashMap;
import java.util.Map;

import com.rjcass.simpleservice.BSIException;
import com.rjcass.simpleservice.BusinessService;
import com.rjcass.simpleservice.BusinessServiceManager;
import com.rjcass.simpleservice.ServiceReply;
import com.rjcass.simpleservice.ServiceRequest;

public class BasicBusinessServiceManager extends
		BusinessServiceManager
{
	private Map<String, BusinessService> mServices;
	private ServiceRequestFactory mRequestFactory;
	private ServiceReplyFactory mReplyFactory;

	public BasicBusinessServiceManager(
			ServiceRequestFactory requestFactory,
			ServiceReplyFactory replyFactory)
	{
		setDelegate(this);
		mRequestFactory = requestFactory;
		mReplyFactory = replyFactory;
		mServices = new HashMap<String, BusinessService>();
	}

	public void setBusinessServicesMap(Map<String, BusinessService> services)
	{
		mServices.putAll(services);
	}

	@Override
	protected BusinessService doGetBusinessService(String name)
	{
		BusinessService service = mServices.get(name);
		if (service == null)
			throw new BSIException("No such BusinessService registered: "
					+ name);
		return service;
	}

	@Override
	protected ServiceRequest doCreateServiceRequest()
	{
		return mRequestFactory.createServiceRequest();
	}

	@Override
	protected ServiceReply doCreateServiceReply()
	{
		return mReplyFactory.createServiceReply();
	}
}
