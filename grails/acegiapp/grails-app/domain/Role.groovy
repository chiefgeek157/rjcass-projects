class Role {
    static hasMany = [people:Person]
    static belongsTo = Person

    String name
    String description

    static def constraints = {
        name(blank:false, unique:true)
        description()
    }
}