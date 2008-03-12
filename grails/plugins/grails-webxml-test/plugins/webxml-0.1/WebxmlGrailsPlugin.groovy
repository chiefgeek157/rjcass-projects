class WebxmlGrailsPlugin {

    def DEFAULT_CONFIG_FILE = "DefaultWebXmlConfig"
    def APP_CONFIG_FILE     = "WebXmlConfig"

    def version = "0.1"
    //def dependsOn = [:]
    //def loadAfter = []
    def author = "Roger Cass"
    def authorEmail = "roger.cass@byu.net"
    def title = "Create useful additions to web.xml"
    def description = "Used the doWithWebDescriptor feature of plugins to create application-configured features otherwise unavailable."
    //def documentation = ""
    def watchedResources = "**/grails-app/conf/${APP_CONFIG_FILE}.groovy"    
	
    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }
   
    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)		
    }

    def doWithWebDescriptor = { xml ->
    
        def config = getConfig()

        if(config) {
            if(config.filterChainProxyDelegator.add) {
                def contextParam = xml."context-param"
                    contextParam[contextParam.size() - 1] + {
                        'filter' {
                            'filter-name'(config.filterChainProxyDelegator.filterName)
                            'filter-class'(config.filterChainProxyDelegator.className)
                            'init-param' {
                                'param-name'('targetBeanName')
                                'param-value'(config.filterChainProxyDelegator.targetBeanName)
                            }
                        }
                    }
        
                def filter = xml."filter"
                    filter[filter.size() - 1] + {
                        'filter-mapping' {
                            'filter-name'(config.filterChainProxyDelegator.filterName)
                            'url-pattern'(config.filterChainProxyDelegator.urlPattern)
                        }
                    }
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
    
    def getConfig = {
        ClassLoader parent = getClass().getClassLoader()
        GroovyClassLoader loader = new GroovyClassLoader(parent)

        def config

        try {
            def defaultConfigFile = loader.loadClass(DEFAULT_CONFIG_FILE)
            //log.info("Loading default config file: "+defaultConfigFile)
            config = new ConfigSlurper().parse(defaultConfigFile)
            
            try {
                def appConfigFile = loader.loadClass(APP_CONFIG_FILE)
                //log.info("Found application config file: "+appConfigFile)
                def appConfig = new ConfigSlurper().parse(appConfigFile)
                if (appConfig) {
                    //log.info("Merging application config file: "+appConfigFile)
                    config = config.merge(appConfig)
                }
            } catch(ClassNotFoundException e) {
                //log.warn("Did not find application config file: "+APP_CONFIG_FILE)
            }
        } catch(ClassNotFoundException e) {
            //log.error("Did not find default config file: "+DEFAULT_CONFIG_FILE)
        }

        config?.webxml
    }

}
