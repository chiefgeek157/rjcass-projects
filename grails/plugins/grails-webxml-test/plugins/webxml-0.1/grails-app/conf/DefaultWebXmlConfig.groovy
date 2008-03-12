/**
 * Default configuration file for WebXml plugin.
 * 
 * To override values, create a file called YOUR-APP/grails-app/conf/WebXmlConfig.groovy
 */
webxml {
    filterChainProxyDelegator.add = false
    filterChainProxyDelegator.targetBeanName = "filterChainProxyDelegate"
    filterChainProxyDelegator.urlPattern = "/*"
    filterChainProxyDelegator.filterName = "filterChainProxyDelegator"
    filterChainProxyDelegator.className = "org.springframework.web.filter.DelegatingFilterProxy"
}