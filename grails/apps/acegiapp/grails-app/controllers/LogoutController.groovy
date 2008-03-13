class LogoutController {

    def index = {
        redirect(uri: "/j_acegi_logout")
    }
}
