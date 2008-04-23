package com.rjcass.simpleservice.basic;

import com.rjcass.simpleservice.ServiceRequest;

public class BasicServiceRequestFactory implements ServiceRequestFactory
{
	public BasicServiceRequestFactory()
	{}

	@Override
	public ServiceRequest createServiceRequest()
	{
		return new BasicServiceRequest();
	}
}
