<table class="layout">
    <tr>
        <td class="layout"><a href="${createLinkTo(dir:'')}"><img
        	src="${createLinkTo(dir:'images/logos', file:'ymyw-100.png')}" alt="YMYW"/></a></td>
        <td class="layout"><div align="right">
        	<g:if test="${!pageProperty(name:'meta.suppressLogin')}">
	            <sess:displayName/> |
                <sess:loginLink/> |
   	            <sess:profileLink/> |
   	        </g:if>
            <sess:language/>
        </td>
    </div></td>
    </tr>
</table>