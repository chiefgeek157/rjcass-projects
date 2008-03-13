import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.ui.logout.LogoutFilter;
import org.acegisecurity.ui.logout.LogoutHandler;
import org.springframework.beans.factory.FactoryBean;

class LogoutFilterFactoryBean implements FactoryBean
{
    String logoutUrl
    LogoutHandler[] handlers

    Object getObject() {
        LogoutFilter filter = new LogoutFilter(logoutUrl, handlers as LogoutHandler[])
        filter
    }

    Class getObjectType() {
        return LogoutFilter.class
    }

    boolean isSingleton() {
        return true
    }

    String getLogoutUrl() {
        return g.createLinkTo(file:logoutUrl)
    }

    void setLogoutUrl(String url) {
        logoutUrl = url;
    }

    List getHandlers() {
        return handlers
    }
    
    void setHandlers(List handlers) {
        this.handlers = handlers;
    }
}