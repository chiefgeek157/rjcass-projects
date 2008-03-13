import org.acegisecurity.GrantedAuthority
import org.acegisecurity.userdetails.User

class GroovyUserDetails extends User {

    GroovyUserDetails(String username, String passwordHash, boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired,
            boolean accountNonLocked, GrantedAuthority[] auths, Person person) {
        super(username, passwordHash, enabled, accountNonExpired, credentialsNonExpired,
            accountNonLocked, auths)
        
        
    }
    
    Person getPerson() {
        return person
    }
    
    def getSalt() {
        person.username
    }
}