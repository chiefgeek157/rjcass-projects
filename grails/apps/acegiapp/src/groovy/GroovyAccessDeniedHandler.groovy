import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.acegisecurity.AccessDeniedException
import org.acegisecurity.ui.AccessDeniedHandler
import org.acegisecurity.ui.AccessDeniedHandlerImpl

class GroovyAccessDeniedHandler implements AccessDeniedHandler {

    String errorPage;

    void handle(ServletRequest request, ServletResponse response, AccessDeniedException accessDeniedException) {
        if (errorPage != null) {
            // Put exception into request scope (perhaps of use to a view)
            ((HttpServletRequest)request).setAttribute(AccessDeniedHandlerImpl.ACEGI_SECURITY_ACCESS_DENIED_EXCEPTION_KEY,
                accessDeniedException)

            // Perform RequestDispatcher "forward"
            grailsApplication.redirect(errorPage)
        }

        if (!response.isCommitted()) {
            // Send 403 (we do this after response has been written)
            ((HttpServletResponse)response).sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        }
    }

    def setErrorPage(String page) {
        if (page != null && !page.startsWith("/")) {
            throw new IllegalArgumentException("ErrorPage must begin with '/'");
        }

        errorPage = page;
    }
}
