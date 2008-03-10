            
class RoleController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        if(!params.max) params.max = 10
        [ roleList: Role.list( params ) ]
    }

    def show = {
        def role = Role.get( params.id )

        if(!role) {
            flash.message = "Role not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ role : role ] }
    }

    def delete = {
        def role = Role.get( params.id )
        if(role) {
            role.delete()
            flash.message = "Role ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "Role not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def role = Role.get( params.id )

        if(!role) {
            flash.message = "Role not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ role : role ]
        }
    }

    def update = {
        def role = Role.get( params.id )
        if(role) {
            role.properties = params
            if(!role.hasErrors() && role.save()) {
                flash.message = "Role ${params.id} updated"
                redirect(action:show,id:role.id)
            }
            else {
                render(view:'edit',model:[role:role])
            }
        }
        else {
            flash.message = "Role not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def role = new Role()
        role.properties = params
        return ['role':role]
    }

    def save = {
        def role = new Role(params)
        if(!role.hasErrors() && role.save()) {
            flash.message = "Role ${role.id} created"
            redirect(action:show,id:role.id)
        }
        else {
            render(view:'create',model:[role:role])
        }
    }
}