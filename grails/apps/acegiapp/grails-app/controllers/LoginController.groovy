import org.acegisecurity.DisabledException
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;

class LoginController {

    //AcegiHelperService helper

    def index = {
        //if (isLoggedIn()) {
        //    redirect(uri: "/")
        //} else {
            redirect(action:authenticate,params:params)
        //}
    }

    def authenticate = {
        //modifyHeader(response)
        //if (isLoggedIn()) {
        //    redirect(uri: "/")
        //}
    }

    def failed = {
        /**
        def username = ""
        if (session[AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY]!=null)
            username = session[AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY]
        def message = ""
        if (session[AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY]) {
            def exception = session[AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY]
            if (exception instanceof DisabledException) {
                message = g.message(code:'LoginController.authorizationFailed.disabled',args='$username')
            } else {
                message = g.message(code:'LoginController.authorizationFailed.failed')
            }
        }
        */

        def message = g.message(code:'LoginController.failed.failed')
        flash.message = message
        redirect(action:authenticate,params:params)
    }

    def denied = {
        redirect(uri: "/")
    }	

    //def isLoggedIn() {
    //    def authPrincipal = helper.principal()
    //    return (authPrincipal != null && authPrincipal != "anonymousUser")
    //}

    //def modifyHeader(response) {
    //    response.setHeader("Cache-Control", "no-cache")
    //    response.addHeader("Cache-Control", "private")
    //    response.addDateHeader("Expires", 0)
    //    response.setDateHeader("max-age", 0)
    //    response.setIntHeader("Expires", -1)
    //}
}