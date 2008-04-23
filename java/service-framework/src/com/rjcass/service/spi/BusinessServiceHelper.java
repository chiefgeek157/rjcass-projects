package com.rjcass.service.spi;

import com.rjcass.service.BusinessServiceManager;

public interface BusinessServiceHelper
{
	BusinessServiceManager getBusinessServiceManager();
	ServiceRequest createServiceRequest();
	ServiceReply createServiceReply();
}
