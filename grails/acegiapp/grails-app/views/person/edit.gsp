

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Person</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Person List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Person</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Person</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${person}">
            <div class="errors">
                <g:renderErrors bean="${person}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${person?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="username">Username:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:person,field:'username','errors')}">
                                    <input type="text" id="username" name="username" value="${fieldValue(bean:person,field:'username')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="passwordHash">Password Hash:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:person,field:'passwordHash','errors')}">
                                    <input type="text" id="passwordHash" name="passwordHash" value="${fieldValue(bean:person,field:'passwordHash')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="displayName">Display Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:person,field:'displayName','errors')}">
                                    <input type="text" id="displayName" name="displayName" value="${fieldValue(bean:person,field:'displayName')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="enabled">Enabled:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:person,field:'enabled','errors')}">
                                    <g:checkBox name="enabled" value="${person?.enabled}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="accountNonExpired">Account Non Expired:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:person,field:'accountNonExpired','errors')}">
                                    <g:checkBox name="accountNonExpired" value="${person?.accountNonExpired}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="credentialsNonExpired">Credentials Non Expired:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:person,field:'credentialsNonExpired','errors')}">
                                    <g:checkBox name="credentialsNonExpired" value="${person?.credentialsNonExpired}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="accountNonLocked">Account Non Locked:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:person,field:'accountNonLocked','errors')}">
                                    <g:checkBox name="accountNonLocked" value="${person?.accountNonLocked}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email">Email:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:person,field:'email','errors')}">
                                    <input type="text" id="email" name="email" value="${fieldValue(bean:person,field:'email')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated">Date Created:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:person,field:'dateCreated','errors')}">
                                    <g:datePicker name="dateCreated" value="${person?.dateCreated}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastUpdated">Last Updated:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:person,field:'lastUpdated','errors')}">
                                    <g:datePicker name="lastUpdated" value="${person?.lastUpdated}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="roles">Roles:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:person,field:'roles','errors')}">
                                    
<ul>
<g:each var="r" in="${person?.roles?}">
    <li><g:link controller="role" action="show" id="${r.id}">${r}</g:link></li>
</g:each>
</ul>
<g:link controller="role" params="["person.id":person?.id]" action="create">Add Role</g:link>

                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
