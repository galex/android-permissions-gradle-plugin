package il.co.galex.tools.permissions.model

import java.util.regex.Pattern

import static java.util.regex.Pattern.*

/**
 * Configuration extension of the plugin, including packageName and className validation.
 *
 * @author Alexander Gherschon
 */
class PermissionsExtension {

    private static final Pattern PACKAGE_IDENTIFIER = compile("(\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*\\.)*\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*");
    private static final Pattern CLASS_IDENTIFIER = compile("[A-Z_](\$[A-Z_]|[\\w_])*");
    private static final String ADVICE = ", please fix it or remove it from the permissions plugin configuration";

    private String helperPackage
    private String helperClassName

    public String getHelperPackage(){
        return helperPackage
    }

    void setHelperPackage (String str){

        if(PACKAGE_IDENTIFIER.matcher(str).matches()) helperPackage = str
        else throw new IllegalArgumentException("helperPackage is not a valid package name" + ADVICE)
    }

    public String getHelperClassName(){
        return helperClassName
    }

    void setHelperClassName (String str){

        if(CLASS_IDENTIFIER.matcher(str).matches()) helperClassName = str
        else throw new IllegalArgumentException("helperClassName is not a valid class name" + ADVICE)
    }
}
