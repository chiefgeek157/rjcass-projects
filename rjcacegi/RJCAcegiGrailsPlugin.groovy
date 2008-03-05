
class RJCAcegiGrailsPlugin {
    def version = 0.1
    def dependsOn = [:]
	
    def doWithSpring = {
        def filters = [
           "httpSessionContextIntegrationFilter",
           "logoutFilter",
           "authenticationProcessingFilter",
           "securityContextHolderAwareRequestFilter",
           "rememberMeProcessingFilter",
           "anonymousProcessingFilter",
           "exceptionTranslationFilter",
           "filterInvocationInterceptor"
        ]

        /** Filter Chain Proxy referenced by Spring Delegating Proxy Filter in web.xml */
        filterChainProxy(org.acegisecurity.util.FilterChainProxy) {
            filterInvocationDefinitionSource = """
            CONVERT_URL_TO_LOWERCASE_BEFORE_COMPARISON
            PATTERN_TYPE_APACHE_ANT
            /**=${filters.join(',')}
            """
        }
        
            /** 
        httpSessionContextIntegrationFilter(org.acegisecurity.context.HttpSessionContextIntegrationFilter) {
        }

        logoutFilter(org.acegisecurity.ui.logout.LogoutFilter, "/logout.gsp",
            [ref("rememberMeServices"),ref("securityContextLogoutHandler")]) {
        }

        authenticationProcessingFilter(org.acegisecurity.ui.webapp.AuthenticationProcessingFilter) {
            authenticationManager = ref("providerManager")
            authenticationFailureUrl = "/login/failed"
            defaultTargetUrl = "/"
            filterProcessesUrl = "/j_acegi_security_check"
            rememberMeServices = ref("rememberMeServices")
        }

        securityContextHolderAwareRequestFilter(org.acegisecurity.wrapper.SecurityContextHolderAwareRequestFilter) {
        }

        rememberMeProcessingFilter(org.acegisecurity.ui.rememberme.RememberMeProcessingFilter) {
            authenticationManager = ref("providerManager")
            rememberMeServices = ref("rememberMeServices")
        }

        anonymousProcessingFilter(org.acegisecurity.providers.anonymous.AnonymousProcessingFilter) {
            key = "anonymous key"
            userAttribute = "anonymousUser,ROLE_ANONYMOUS"
        }

        exceptionTranslationFilter(org.acegisecurity.ui.ExceptionTranslationFilter) {
            authenticationEntryPoint = ref("authenticationEntryPoint")
            accessDeniedHandler = ref("accessDeniedHandler")
        }
        
        providerManager(org.acegisecurity.providers.ProviderManager) {
            providers = [
                ref("daoAuthenticationProvider"),
                ref("anonymousAuthenticationProvider"),
                ref("rememberMeAuthenticationProvider")]
        }
        
        daoAuthenticationProvider(org.acegisecurity.providers.dao.DaoAuthenticationProvider) {
            userDetailsService = ref("groovyUserDetailsService")
            passwordEncoder = ref("messageDigestPasswordEncoder")
            saltSource = ref("groovyUserDetailsService")
            userCache = ref("ehUserCache")
        }
        
        groovyUserDetailsService(com.rjcass.grails.plugin.acegi.GroovyUserDetailsService) {
        }
        
        messageDigestPasswordEncoder(org.acegisecurity.providers.encoding.MessageDigestPasswordEncoder, "MD5") {
            encodeHashAsBase64 = true
        }
        
        ehUserCache(org.acegisecurity.providers.dao.cache.EhCacheBasedUserCache) {
            cache = ref("ehCacheFactory")
        }
        
        ehCacheFactory(org.springframework.cache.ehcache.EhCacheFactoryBean) {
            cacheManager = ref("ehCacheManagerFactory")
            cacheName = "GroovyUserDetailsCache"
        }

        ehCacheManagerFactory(org.springframework.cache.ehcache.EhCacheManagerFactoryBean) {}

        anonymousAuthenticationProvider(org.acegisecurity.providers.anonymous.AnonymousAuthenticationProvider) {
            key = "anonymous key"
        }

        rememberMeAuthenticationProvider(org.acegisecurity.providers.rememberme.RememberMeAuthenticationProvider) {
            key = "remember me key"
        }
        
        authenticationEntryPoint(org.acegisecurity.ui.webapp.AuthenticationProcessingFilterEntryPoint) {
            loginFormUrl = "/login/authenticate"
            forceHttps = true
        }
        
        accessDeniedHandler(org.codehaus.groovy.grails.plugins.acegi.GrailsAccessDeniedHandlerImpl) {
            errorPage = "/accessDenied.gsp"
        }
        
        securityContextLogoutHandler(org.acegisecurity.ui.logout.SecurityContextLogoutHandler){
        }
        */
   }
   
    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)		
    }

    def doWithWebDescriptor = { xml ->
        def contextParam = xml."context-param"
        contextParam[contextParam.size() - 1] + {
            'filter' {
                'filter-name'('delegatorToAcegiFilterChainProxy')
                'filter-class'('org.springframework.web.filter.DelegatingFilterProxy')
                'init-param' {
                    'param-name'('targetClass')
                    'param-value'('org.acegisecurity.util.FilterChainProxy')
                }
            }
        }
    
        def filter = xml."filter"
        filter[filter.size() - 1] + {
            'filter-mapping' {
                'filter-name'('delegatorToAcegiFilterChainProxy')
                'url-pattern'("/*")
            }
        }
    }
	                                      
    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }
	
    def onChange = { event ->
        // TODO Implement code that is executed when this class plugin class is changed  
        // the event contains: event.application and event.applicationContext objects
    }
                                                                                  
    def onApplicationChange = { event ->
        // TODO Implement code that is executed when any class in a GrailsApplication changes
        // the event contain: event.source, event.application and event.applicationContext objects
    }
}
