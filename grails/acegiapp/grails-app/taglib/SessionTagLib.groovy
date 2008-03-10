import org.acegisecurity.AuthenticationTrustResolverImpl as ATR
import org.acegisecurity.context.SecurityContextHolder as SCH
import org.acegisecurity.providers.anonymous.AnonymousAuthenticationToken

class SessionTagLib {
    static namespace = "sess"

    def groovyUserDetailsService
    
    def ifLoggedIn = {attrs, body ->
        if (isLoggedIn()) {
            out << body
        }
    }
    
    def displayName = {attrs, body ->
        if(isLoggedIn()) {
            def auth = getAuthentication()
            def person = groovyUserDetailsService.findPersonByUserDetails(auth.principal)
            out << person.displayName
        } else {
            out << g.message(code: 'SessionTagLib.displayName.guest')
        }
    }

    def loginLink = {attrs, body ->
        if (isLoggedIn()) {
            out << "<a href=\"${g.createLink(controller:'logout')}\">"+
            "${g.message(code: 'SessionTagLib.loginLink.logout')}"+
            "</a>"
        }
        else {
            out << "<a href=\""+g.createLinkTo(dir:"",file:"login")+"\">" +
            g.message(code: 'SessionTagLib.loginLink.login') + "</a>"
        }
    }

    def profileLink = {attrs, body ->
        if (isLoggedIn()) {
            out << "<a href=\"${g.createLink(controller:'profile')}\">"+
            "${g.message(code: 'SessionTagLib.profileLink.profile')}"+
            "</a>"
            //out << g.message(code: 'SessionTagLib.profileLink.profile')
        }
        else {
            out << g.message(code: 'SessionTagLib.profileLink.notLoggedIn')
        }
    }

    def language = {attrs, body ->
        out << "<select>"
        out << "<option value=\"en\">${g.message(code:'SessionTagLib.language.en')}</value>"
        out << "<option value=\"es\">Espa&#241;ol</value>"
        out << "<option value=\"fr\">Fran&#231;ais</value>"
        out << "</select>"
    }
    
    def isLoggedIn() {
        (!(getAuthentication() instanceof AnonymousAuthenticationToken))
    }
    
    def getAuthentication() {
        SCH?.context?.authentication
    }
}