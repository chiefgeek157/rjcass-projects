import org.acegisecurity.providers.encoding.MessageDigestPasswordEncoder

class BootStrap {
    
    def init = { servletContext ->
        def administratorRole = createRole("Administrator","System administrators")
        def userRole = createRole("Uer","Authenticated users")
        def admin = createPerson("admin","admin","Administrator",[administratorRole])
        def roger = createPerson("roger","abc","Roger Cass",[userRole])
    }
    
    def destroy = {
    }
    
    def createRole(name,description) {
        def role = new Role(name:name,description:description).save()
        log.debug("Added Role: "+name+", "+role)
        role
    }
    
    def createPerson(username,password,displayName,roles) {
        def encoder = new MessageDigestPasswordEncoder("MD5", true)
        def passwordHash = encoder.encodePassword(password,username)
        def person = new Person()
        person.username = username
        person.passwordHash = passwordHash
        person.displayName = displayName
        person.enabled = true
        person.accountNonExpired = true
        person.credentialsNonExpired = true
        person.accountNonLocked = true
        roles.each{person.addToRoles(it)}
        person.save()
        log.debug("Person added: "+person)
        person
    }
} 