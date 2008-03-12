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

    filterChainProxyDelegate(org.acegisecurity.util.FilterChainProxy) {
        filterInvocationDefinitionSource = """
        CONVERT_URL_TO_LOWERCASE_BEFORE_COMPARISON
        PATTERN_TYPE_APACHE_ANT
        /**=${filters.join(',')}
        """
    }
}