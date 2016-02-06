package il.co.galex.permissions.util

import com.android.build.gradle.api.BaseVariant
import il.co.galex.permissions.model.DangerousPermission

/**
 * Created by galex on 06/02/16.
 */
class FilterUtils {


    public static ArrayList<DangerousPermission> getRequiredPermissions(File manifestOutFile) {

        def requiredPermissionList = new ArrayList()

        //println "Permissions [${variant.name}] manifestOutFile: ${manifestOutFile}"
        if (manifestOutFile != null && manifestOutFile.exists()) {
            // read the list of permissions out of it
            def manifest = new XmlSlurper().parse(manifestOutFile)
            def permissionList = manifest.'**'.findAll { it.name() == 'uses-permission' }
            //println "Permissions [${variant.name}] list of permissions: ${permissionList}"
            //println "Permissions [${variant.name}] list of permissions size: ${permissionList.size()}"
            //def requiredPermissionList = new ArrayList()
            permissionList.each { it ->
                def permission = it["@android:name"].toString().tokenize('.')[-1]
                //println "Permission = " + permission
                def dangerousPermission = DangerousPermission.get(permission)
                //println "Dangerous Permission contains = " + dangerousPermission
                if (dangerousPermission != null) requiredPermissionList.add(dangerousPermission)
            }
            //println "requiredPermissionList = " + requiredPermissionList
        }

        requiredPermissionList
    }
}
