<html>
<head>
    <!-- To use in a GSP, use <meta name="layout" content="main"/> -->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><g:layoutTitle default="Reference Application"/></title>
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'grails.css')}"/>
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'main.css')}"/>
    <!-- <link rel="icon" href="${createLinkTo(dir: 'images', file: 'favicon.ico')}" type="image/x-icon" /> -->
    <g:layoutHead/>
</head>
<body>
<table class="layout" id="layout.outer">
    <tr>
        <td class="layout"><layout:template name="meta.headerTemplate" default="/templates/header"/></td>
    </tr>
    <tr>
        <td class="layout"><table class="layout" id="layout.middle">
            <tr>
                <td class="layout" width="200px"><table class="layout" id="layout.left">
                    <tr>
                        <td class="layout"><layout:template name="meta.leftTemplate" default="/templates/left"/></td>
                    <tr>
                </table></td>
                <td class="layout"><table class="layout" id="layout.body">
                    <tr>
                        <td class="layout"><g:layoutBody/></td>
                    </tr>
                </table></td>
                <td class="layout"><table class="layout" id="layout.right">
                    <tr>
                        <td class="layout"><layout:template name="meta.rightTemplate" default="/templates/right"/></td>
                    </tr>
                </table></td>
            </tr>
        </table></td>
    </tr>
    <tr>
        <td class="layout"><table class="layout" id="layout.footer">
            <tr>
                <td class="layout"><layout:template name="meta.footerTemplate" default="/templates/footer"/></td>
            </tr>
        </table></td>
    </tr>
</table>
</body>
</html>