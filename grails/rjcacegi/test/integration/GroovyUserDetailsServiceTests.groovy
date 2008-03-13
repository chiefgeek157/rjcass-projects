import com.rjcass.grails.plugin.acegi.GroovyUserDetailsService

import org.acegisecurity.userdetails.UsernameNotFoundException

class GroovyUserDetailsServiceTests extends GroovyTestCase {

    void testCreateInstance() {
        def service = new GroovyUserDetailsService()
        shouldFail({service.loadUserByUsername("not real")})
    }
    
    void testGetDefaultSalt() {
        def service = new GroovyUserDetailsService()
        assert service.getSalt() == "too salty"
    }
}