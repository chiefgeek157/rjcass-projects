class LayoutTagLib {
    static namespace = "layout"

    def template = {attrs, body ->
        if (attrs.name == "" && attrs.default == "") {
            out << g.message(code: 'LayoutTagLib.template.missingAttr')
        }
        else {
            def templateName = attrs.default
            if (attrs.name != "") {
                def propertyValue = pageProperty(name: attrs.name)
                if (propertyValue != "") {
                    templateName = propertyValue
                }
            }
            out << render(template: templateName)
        }
    }
}