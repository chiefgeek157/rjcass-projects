import org.acegisecurity.GrantedAuthorityImpl
import org.acegisecurity.providers.dao.DaoAuthenticationProvider
import org.acegisecurity.providers.dao.SaltSource
import org.acegisecurity.userdetails.User
import org.acegisecurity.userdetails.UserDetails
import org.acegisecurity.userdetails.UserDetailsService
import org.acegisecurity.userdetails.UsernameNotFoundException
import org.acegisecurity.vote.RoleVoter
import org.hibernate.SessionFactory
import org.springframework.orm.hibernate3.SessionFactoryUtils
import org.springframework.orm.hibernate3.SessionHolder
import org.springframework.transaction.support.TransactionSynchronizationManager
import org.springframework.web.context.support.WebApplicationObjectSupport

public class GroovyUserDetailsService extends WebApplicationObjectSupport
    implements UserDetailsService, SaltSource {

    boolean transactional = true
    
    UserDetails loadUserByUsername(String username) {
        def sessionFactory = (SessionFactory)getWebApplicationContext().getBean("sessionFactory");
        def releaseSession = true
        def sessionHolder
        def session
        if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
            log.debug("Session already has transaction attached");
            releaseSession = false;
            session = ((SessionHolder)TransactionSynchronizationManager.getResource(sessionFactory)).getSession();
        } else {
            log.debug("Session does not have transaction attached... Creating new one");
            session = SessionFactoryUtils.getSession(sessionFactory, true);
            sessionHolder = new SessionHolder(session);
            TransactionSynchronizationManager.bindResource(sessionFactory, sessionHolder);
        }

        log.debug("Looking for Person with username: ${username}")
        def person = Person.findByUsername(username)
        log.debug("Found Person ${person}")
        log.debug("Person.roles ${person.roles}")
        def details
        if(person) {
            def roleVoter = applicationContext.getBean('roleVoter')
            GrantedAuthorityImpl[] auths = new GrantedAuthorityImpl[person.roles.size()]
            def rolePrefix = roleVoter.getRolePrefix()
            def i = 0
            person.roles.each {
                auths[i++] = new GrantedAuthorityImpl("${rolePrefix}${it.name.toUpperCase()}")
            }
            details = new User(person.username, person.passwordHash,
                person.enabled, person.accountNonExpired, person.credentialsNonExpired,
                person.accountNonLocked, auths)
        } else {
            log.debug("Person with username '"+username+"' not found")
            throw new UsernameNotFoundException(username)
        }

        if (releaseSession) {
            TransactionSynchronizationManager.unbindResource(sessionFactory);
            SessionFactoryUtils.releaseSession(session, sessionFactory);
            log.debug("Session released");
        }
        details
    }
    
    def getSalt(UserDetails details) {
        details.username
    }
    
    String encodePassword(String username, String password) {
        def encoder = authProvider.getPasswordEncoder()
        encoder.encodePassword(password,username)
    }
    
    def findPersonByUserDetails(UserDetails details) {
        def person = Person.findByUsername(details.username)
        person
    }
}