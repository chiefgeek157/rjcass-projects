package com.rjcass.service.spi.basic;

import com.rjcass.service.spi.ServiceReply;

public class BasicServiceReplyFactory implements ServiceReplyFactory
{
	public BasicServiceReplyFactory()
	{}

	@Override
	public ServiceReply createServiceReply()
	{
		return new BasicServiceReply();
	}

}
