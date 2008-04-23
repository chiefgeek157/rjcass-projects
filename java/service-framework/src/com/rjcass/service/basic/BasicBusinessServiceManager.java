package com.rjcass.service.basic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.rjcass.service.BSIException;
import com.rjcass.service.BusinessService;
import com.rjcass.service.BusinessServiceManager;

public class BasicBusinessServiceManager extends BusinessServiceManager
{
	private Map<Class<? extends BusinessService>, BusinessService> mServices;

	public BasicBusinessServiceManager()
	{
		setDelegate(this);
		mServices = new HashMap<Class<? extends BusinessService>, BusinessService>();
	}

	public void setBusinessServicesMap(Map<String, BusinessService> services)
	{
		for (Map.Entry<String, BusinessService> entry : services.entrySet())
		{
			try
			{
				Class<? extends BusinessService> iface = Class.forName(
						entry.getKey()).asSubclass(BusinessService.class);
				if (iface == BusinessService.class)
					throw new BSIException("Cannot register using '"
							+ BusinessService.class
							+ "' as the registration interface.");
				BusinessService service = entry.getValue();
				if (!iface.isAssignableFrom(service.getClass()))
					throw new BSIException("Service implementation '"
							+ service.getClass()
							+ "' does not implement given interface '" + iface
							+ "'");
				mServices.put(iface, service);
			}
			catch (ClassNotFoundException e)
			{
				throw new BSIException(e);
			}
		}
	}

	public void setServicesSet(Set<BusinessService> services)
	{
		for (BusinessService service : services)
		{
			Set<Class<? extends BusinessService>> ifaces = findServices(service);
			for (Class<? extends BusinessService> iface : ifaces)
			{
				mServices.put(iface, service);
			}
		}
	}

	@Override
	protected BusinessService doGetBusinessService(String ifName)
	{
		Class<? extends BusinessService> iface;
		try
		{
			iface = Class.forName(ifName).asSubclass(BusinessService.class);
		}
		catch (ClassNotFoundException e)
		{
			throw new BSIException(e);
		}
		return doGetBusinessService(iface);
	}

	@Override
	protected BusinessService doGetBusinessService(
			Class<? extends BusinessService> iface)
	{
		BusinessService service = mServices.get(iface);
		if (service == null)
			throw new BSIException("Service not found for interface: " + iface);
		return service;
	}

	private Set<Class<? extends BusinessService>> findServices(
			BusinessService service)
	{
		Set<Class<? extends BusinessService>> ifaces = new HashSet<Class<? extends BusinessService>>();

		Queue<Class<?>> classesToCheck = new LinkedList<Class<?>>();
		classesToCheck.add(service.getClass());

		while (classesToCheck.size() > 0)
		{
			Class<?> classToCheck = classesToCheck.remove();
			if (classToCheck.isInterface())
				checkInterface(classToCheck, classesToCheck, ifaces);
			else
				checkClass(classToCheck, classesToCheck);
		}

		return ifaces;
	}

	private void checkInterface(Class<?> cls, Queue<Class<?>> queue,
			Set<Class<? extends BusinessService>> ifaces)
	{
		Class<?>[] interfaces = cls.getInterfaces();
		for (int i = 0; i < interfaces.length; i++)
		{
			if (interfaces[i] == BusinessService.class)
			{
				// The interface Class passed in is a direct child of Service
				ifaces.add(cls.asSubclass(BusinessService.class));
			}
			else
			{
				queue.add(interfaces[i]);
			}
		}
	}

	private void checkClass(Class<?> cls, Queue<Class<?>> queue)
	{
		Class<?> superCls = cls.getSuperclass();
		Class<?>[] interfaces = cls.getInterfaces();

		if (superCls != null)
		{
			queue.add(superCls);
		}

		for (int i = 0; i < interfaces.length; i++)
		{
			queue.add(interfaces[i]);
		}
	}
}
