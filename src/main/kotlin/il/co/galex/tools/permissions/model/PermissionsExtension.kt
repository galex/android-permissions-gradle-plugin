package il.co.galex.tools.permissions.model

import java.util.regex.Pattern.compile

class PermissionsExtension {

    companion object {
        private val PACKAGE_IDENTIFIER = compile("(\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*\\.)*\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*")
        private val CLASS_IDENTIFIER = compile("[A-Z_](\$[A-Z_]|[\\w_])*")
        private val ADVICE = ", please fix it or remove it from the permissions plugin configuration"
    }

    var helperPackage: String? = null
        set(value) {
            return if (PACKAGE_IDENTIFIER.matcher(value).matches()) field = value
            else throw IllegalArgumentException("helperPackage is not a valid package name" + ADVICE)
        }

    var helperClassName: String? = null
        set(value) {
            return if (CLASS_IDENTIFIER.matcher(value).matches()) field = value
            else throw IllegalArgumentException("helperClassName is not a valid class name" + ADVICE)
        }

}