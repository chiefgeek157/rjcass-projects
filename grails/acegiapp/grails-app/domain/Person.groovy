class Person {
    
    static hasMany = [roles:Role,groups:Group]
    static belongsTo = Group

    Date dateCreated
    Date lastUpdated
    
    // Required fields
    String username
    String passwordHash
    boolean enabled
    boolean accountNonExpired
    boolean credentialsNonExpired
    boolean accountNonLocked

    // Additional fields
    String displayName
    String email

    static def constraints = {
        username(blank:false,unique:true,validator:{
            return(it.length()>=5 && it.length()<=20)
        })
        passwordHash(blank:false)
        displayName(blank:false)
        enabled()
        accountNonExpired()
        credentialsNonExpired()
        accountNonLocked()
        email(email:true,blank:true,nullable:true)
    }
}