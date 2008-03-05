package com.rjcass.grails.plugin.acegi

import org.acegisecurity.providers.dao.SaltSource
import org.acegisecurity.userdetails.UserDetails
import org.acegisecurity.userdetails.UserDetailsService
import org.acegisecurity.userdetails.UsernameNotFoundException
public class GroovyUserDetailsService implements UserDetailsService, SaltSource {

    def saltMethodName
    def defaultSalt = "too salty"
    
    UserDetails loadUserByUsername(String username) {
        if(true) {
            throw new UsernameNotFoundException(username)
        } else {
            log.info("Located: "+username)
        }
    }
    
    def getSalt(UserDetails details) {
        def salt = defaultSalt
        if(saltMethodName) {
            salt = details."$saltMethodName"()
        }
        salt
    }
}