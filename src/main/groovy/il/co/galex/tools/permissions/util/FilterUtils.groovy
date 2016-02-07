package il.co.galex.tools.permissions.util

import il.co.galex.tools.permissions.model.DangerousPermission

/**
 * Manifest Utility class
 *
 * @author Alexander Gherschon
 */
class FilterUtils {

    /**
     * Extracts the name of the permissions from a Manifest File
     * @param manifestOutFile Manifest File
     * @return list of Dangerous Permissions declared in the Manifest File
     */
    public static ArrayList<DangerousPermission> getRequiredPermissions(File manifestOutFile) {

        def requiredPermissionList = new ArrayList()

        if (manifestOutFile != null && manifestOutFile.exists()) {

            def manifest = new XmlSlurper().parse(manifestOutFile)
            def permissionList = manifest.'**'.findAll { it.name() == 'uses-permission' }

            permissionList.each { it ->
                def permission = it["@android:name"].toString().tokenize('.')[-1]
                def dangerousPermission = DangerousPermission.get(permission)
                if (dangerousPermission != null) requiredPermissionList.add(dangerousPermission)
            }
        }
        requiredPermissionList
    }
}
