package il.co.galex.permissions.model

import java.util.regex.Pattern

import static java.util.regex.Pattern.*

/**
 * Created by galex on 07/02/16.
 */
class PermissionsExtension {

    private static final Pattern PACKAGE_IDENTIFIER = compile("(\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*\\.)*\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*");
    private static final Pattern CLASS_IDENTIFIER = compile("[A-Z_](\$[A-Z_]|[\\w_])*");

    private String helperPackage
    private String helperClassName

    public String getHelperPackage(){
        return helperPackage
    }

    void setHelperPackage (String str){

        if(PACKAGE_IDENTIFIER.matcher(str).matches()) helperPackage = str
        else throw new IllegalArgumentException("helperPackage is not a valid fully qualified Java name")
    }

    public String getHelperClassName(){
        return helperClassName
    }

    void setHelperClassName (String str){

        if(CLASS_IDENTIFIER.matcher(str).matches()) helperClassName = str
        else throw new IllegalArgumentException("helperClassName is not a valid fully qualified Java name")
    }
}
