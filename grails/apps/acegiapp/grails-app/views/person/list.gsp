

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Person List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Person</g:link></span>
        </div>
        <div class="body">
            <h1>Person List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="username" title="Username" />
                        
                   	        <g:sortableColumn property="passwordHash" title="Password Hash" />
                        
                   	        <g:sortableColumn property="displayName" title="Display Name" />
                        
                   	        <g:sortableColumn property="enabled" title="Enabled" />
                        
                   	        <g:sortableColumn property="accountNonExpired" title="Account Non Expired" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${personList}" status="i" var="person">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${person.id}">${person.id?.encodeAsHTML()}</g:link></td>
                        
                            <td>${person.username?.encodeAsHTML()}</td>
                        
                            <td>${person.passwordHash?.encodeAsHTML()}</td>
                        
                            <td>${person.displayName?.encodeAsHTML()}</td>
                        
                            <td>${person.enabled?.encodeAsHTML()}</td>
                        
                            <td>${person.accountNonExpired?.encodeAsHTML()}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${Person.count()}" />
            </div>
        </div>
    </body>
</html>
