package com.rjcass.service.spi.basic;

import com.rjcass.service.spi.ServiceRequest;

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
