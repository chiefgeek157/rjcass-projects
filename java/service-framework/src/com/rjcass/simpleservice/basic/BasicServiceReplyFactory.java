package com.rjcass.simpleservice.basic;

import com.rjcass.simpleservice.ServiceReply;

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
