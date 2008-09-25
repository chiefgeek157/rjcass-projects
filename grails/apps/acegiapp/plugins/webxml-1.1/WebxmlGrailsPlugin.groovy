/**
 * Copyright 2008 Roger Cass (roger.cass@byu.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class WebxmlGrailsPlugin {

    def DEFAULT_CONFIG_FILE = "DefaultWebXmlConfig"
    def APP_CONFIG_FILE = "WebXmlConfig"

    def version = "1.1"
    def author = "Roger Cass"
    def authorEmail = "roger.cass@byu.net"
    def title = "Create useful additions to web.xml"
    def description = "Used the doWithWebDescriptor feature of plugins to create application-configured features otherwise unavailable."
    def documentation = "http://grails.org/WebXML+Plugin"
    def watchedResources = "**/grails-app/conf/${APP_CONFIG_FILE}.groovy"

    def grailsVersion = grails.util.GrailsUtil.getGrailsVersion()
    def dependsOn = [urlMappings:grailsVersion]
    def loadAfter = ['urlMappings']

    def doWithSpring = {
    }

    def doWithApplicationContext = {applicationContext ->
    }

    def doWithWebDescriptor = {xml ->

        def config = getConfig()

        if (config) {
            if (config.filterChainProxyDelegator.add) {
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
                        'dispatcher'("REQUEST")
                        'dispatcher'("FORWARD")
                        'dispatcher'("INCLUDE")
                    }
                }
            }

            if (true) {
                def filters = xml.filter
                filters.each {
                    log.debug("Loaded filter: "+it."filter-name".text())
                }
                def filter = xml."filter"
                filter[filter.size() - 1] + {
                    'filter-mapping' {
                        'filter-name'("sitemesh")
                        'url-pattern'("/*")
                        //'dispatcher'("REQUEST")
                        //'dispatcher'("FORWARD")
                        //'dispatcher'("INCLUDE")
                    }
                    'filter-mapping' {
                        'filter-name'("charEncodingFilter")
                        'url-pattern'("/*")
                        //'dispatcher'("REQUEST")
                        //'dispatcher'("FORWARD")
                        //'dispatcher'("INCLUDE")
                    }
                    'filter-mapping' {
                        'filter-name'("grailsWebRequest")
                        'url-pattern'("/*")
                        //'dispatcher'("REQUEST")
                        //'dispatcher'("FORWARD")
                        //'dispatcher'("INCLUDE")
                    }
                    'filter-mapping' {
                        'filter-name'("reloadFilter")
                        'url-pattern'("/*")
                        //'dispatcher'("REQUEST")
                        //'dispatcher'("FORWARD")
                        //'dispatcher'("INCLUDE")
                    }
                    'filter-mapping' {
                        'filter-name'("urlMapping")
                        'url-pattern'("/*")
                        //'dispatcher'("REQUEST")
                        //'dispatcher'("FORWARD")
                        //'dispatcher'("INCLUDE")
                    }
                }
            }
        }
    }

    def doWithDynamicMethods = {ctx ->
    }

    def onChange = {event ->
    }

    def onApplicationChange = {event ->
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
            } catch (ClassNotFoundException e) {
                //log.warn("Did not find application config file: "+APP_CONFIG_FILE)
            }
        } catch (ClassNotFoundException e) {
            //log.error("Did not find default config file: "+DEFAULT_CONFIG_FILE)
        }

        config?.webxml
    }
}
