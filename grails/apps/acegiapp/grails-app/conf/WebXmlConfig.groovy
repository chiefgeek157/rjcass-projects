/**
 * Application configuration file for WebXml plugin.
 */
webxml {
    //========================================
    // Delegating Filter Chain
    //========================================
    // 
    // Add a 'filter chain proxy' delegater as a Filter.  This will allow the application
    // to define a FilterChainProxy bean that can add additional filters, such as
    // an instance of org.acegisecurity.util.FilterChainProxy.
    
    // Set to true to add a filter chain delegator.
    filterChainProxyDelegator.add = true
    
    // The name of the delegate FilterChainProxy bean.  You must ensure you have added a bean
    // witht his name that implements FilterChainProxy to
    // YOUR-APP/grails-app/conf/spring/resources.groovy.
    //filterChainProxyDelegator.targetBeanName = "filterChainProxyDelegate"
    
    // The URL pattern to which the filter will apply.  Usually set to '/*' to cover all URLs.
    //filterChainProxyDelegator.urlPattern = "/*"
    
    //-------------------------------------------------
    // These settings usually do not need to be changed
    //-------------------------------------------------
    
    // The name of the delegating filter.
    //filterChainProxyDelegator.filterName = "filterChainProxyDelegator"
    
    // The delegating filter proxy class.
    //filterChainProxyDelegator.className = "org.springframework.web.filter.DelegatingFilterProxy"
}