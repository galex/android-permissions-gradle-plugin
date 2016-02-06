package il.co.galex.permissions.plugin;

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import il.co.galex.permissions.model.Permission
import org.gradle.api.Plugin
import org.gradle.api.Project

class PermissionsPlugin implements Plugin<Project> {

    @Override void apply(Project project) {

        def hasApp = project.plugins.hasPlugin AppPlugin
        def hasLib = project.plugins.hasPlugin LibraryPlugin
        if (!hasApp && !hasLib) {
            throw new IllegalStateException("'com.android.application' or 'com.android.library' plugin required.")
        }

        def log = project.logger
        def variants
        if (hasApp) {
            variants = project.android.applicationVariants
        } else {
            variants = project.android.libraryVariants
        }

        variants.all { variant ->

            println "Permissions [${variant.name}]"

            variant.outputs.each { output ->

                // find first the manifest for every variant
                println "Permissions [${variant.name}] output: ${output}"
                def manifestOutFile = output.processManifest.manifestOutputFile
                println "Permissions [${variant.name}] manifestOutFile: ${manifestOutFile}"

                // read the list of permissions out of it
                def manifest = new XmlSlurper().parse(manifestOutFile)

                def permissionList = manifest.'**'.findAll{ it.name() == 'uses-permission'}

                println "Permissions [${variant.name}] list of permissions: ${permissionList}"
                println "Permissions [${variant.name}] list of permissions size: ${permissionList.size()}"

                permissionList.each { permission ->

                    println "Permission = " + permission["@android:name"]
                }

                println Permission.INTERNET
            }
        }
    }
}
