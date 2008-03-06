package com.rjcass.grails.plugin.acegi;

import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.ui.logout.LogoutFilter;
import org.acegisecurity.ui.logout.LogoutHandler;
import org.springframework.beans.factory.FactoryBean;

public class LogoutFilterFactoryBean implements FactoryBean
{
    String mLogoutUrl;
    List<LogoutHandler> mHandlers;

    public LogoutFilterFactoryBean()
    {
        mHandlers = new ArrayList<LogoutHandler>();
    }

    @Override public Object getObject() throws Exception
    {
        LogoutHandler[] handlers = new LogoutHandler[mHandlers.size()];
        handlers = mHandlers.toArray(handlers);
        LogoutFilter filter = new LogoutFilter(mLogoutUrl, handlers);
        return filter;
    }

    @Override public Class<?> getObjectType()
    {
        return LogoutFilter.class;
    }

    @Override public boolean isSingleton()
    {
        return true;
    }

    public String getLogoutUrl()
    {
        return mLogoutUrl;
    }

    public void setLogoutUrl(String logoutUrl)
    {
        mLogoutUrl = logoutUrl;
    }

    public List<LogoutHandler> getHandlers()
    {
        return mHandlers;
    }

    public void setHandlers(List<LogoutHandler> handlers)
    {
        mHandlers = handlers;
    }
}
