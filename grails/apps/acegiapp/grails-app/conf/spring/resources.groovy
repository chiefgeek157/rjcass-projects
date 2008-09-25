// Place your Spring DSL code here
beans = {
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

    log.info("Filters: " + "${filters.join(',')}")

    filterChainProxyDelegate(org.acegisecurity.util.FilterChainProxy) {
        filterInvocationDefinitionSource = """
        CONVERT_URL_TO_LOWERCASE_BEFORE_COMPARISON
        PATTERN_TYPE_APACHE_ANT
        /**=${filters.join(',')}
        """
    }

    httpSessionContextIntegrationFilter(org.acegisecurity.context.HttpSessionContextIntegrationFilter) {
    }

    logoutFilter(LogoutFilterFactoryBean) {
        logoutUrl = "/loggedOut.gsp"
        handlers = [ref("rememberMeServices"), ref("securityContextLogoutHandler")]
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

    filterInvocationInterceptor(org.acegisecurity.intercept.web.FilterSecurityInterceptor) {
        authenticationManager = ref("providerManager")
        accessDecisionManager = ref("accessDecisionManager")
        //if (conf.useRequestMapDomainClass) {
        //    objectDefinitionSource = ref("objectDefinitionSource")
        //} else {
        objectDefinitionSource = """
            	CONVERT_URL_TO_LOWERCASE_BEFORE_COMPARISON
            	PATTERN_TYPE_APACHE_ANT
                /=IS_AUTHENTICATED_ANONYMOUSLY
            	/*.gsp=IS_AUTHENTICATED_ANONYMOUSLY
            	/css/**=IS_AUTHENTICATED_ANONYMOUSLY
            	/images/**=IS_AUTHENTICATED_ANONYMOUSLY
            	/js/**=IS_AUTHENTICATED_ANONYMOUSLY
            	/login/**=IS_AUTHENTICATED_ANONYMOUSLY
                /role/**=ROLE_ADMINISTRATOR
            	/**=IS_AUTHENTICATED_FULLY
            	"""
        //}
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

    groovyUserDetailsService(GroovyUserDetailsService) {
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

    rememberMeServices(org.acegisecurity.ui.rememberme.TokenBasedRememberMeServices) {
        userDetailsService = ref("groovyUserDetailsService")
        key = "remember me key"
    }

    rememberMeAuthenticationProvider(org.acegisecurity.providers.rememberme.RememberMeAuthenticationProvider) {
        key = "remember me key"
    }

    authenticationEntryPoint(org.acegisecurity.ui.webapp.AuthenticationProcessingFilterEntryPoint) {
        loginFormUrl = "/login/authenticate"
        forceHttps = false
    }

    accessDeniedHandler(org.acegisecurity.ui.AccessDeniedHandlerImpl) {
        errorPage = "/login/denied"
    }
//    accessDeniedHandler(GroovyAccessDeniedHandler) {
//        errorPage = "/login/denied"
//    }

    securityContextLogoutHandler(org.acegisecurity.ui.logout.SecurityContextLogoutHandler) {
    }

    accessDecisionManager(org.acegisecurity.vote.UnanimousBased) {
        allowIfAllAbstainDecisions = "false"
        decisionVoters = [ref("roleVoter"), ref("authenticatedVoter")]
    }

    roleVoter(org.acegisecurity.vote.RoleVoter) {}

    authenticatedVoter(org.acegisecurity.vote.AuthenticatedVoter) {}

    authenticationLoggerListener(org.acegisecurity.event.authentication.LoggerListener) {}
    authorizationLoggerListener(org.acegisecurity.event.authorization.LoggerListener) {}
}