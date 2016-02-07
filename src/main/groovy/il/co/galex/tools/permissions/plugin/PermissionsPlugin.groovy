package il.co.galex.tools.permissions.plugin;

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import il.co.galex.tools.permissions.model.PermissionsExtension
import il.co.galex.tools.permissions.task.GenerateHelperTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Permissions Plugin.
 * This plugin configures a task to be ran by gradle after the processManifest task which will generate a helper class
 * to help request the Permissions from the user.
 *
 * @author Alexander Gherschon
 */
class PermissionsPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create("permissions", PermissionsExtension)

        def hasApp = project.plugins.hasPlugin AppPlugin
        def hasLib = project.plugins.hasPlugin LibraryPlugin
        if (!hasApp && !hasLib) {
            throw new IllegalStateException("'com.android.application' or 'com.android.library' plugin required.")
        }

        def variants
        if (hasApp) {
            variants = project.android.applicationVariants
        } else {
            variants = project.android.libraryVariants
        }

        variants.all { variant ->
            variant.outputs.each { output ->
                def taskName = "generate${variant.name.capitalize()}PermissionsHelper"
                //println "taskName = " + taskName
                def outputHelper = project.file("$project.buildDir/generated/source/permissions/$variant.name/")
                //println "output = " + outputHelper
                def dependingOnTask = "process${variant.name.capitalize()}Manifest"
                //println "dependingOnTask = " + dependingOnTask

                // find first the manifest for every variant
                //println "Permissions [${variant.name}] output: ${output}"
                File manifestOutFile = output.processManifest.manifestOutputFile
                //println "manifestOutFile = " + manifestOutFile

                def task = project.task(taskName, dependsOn: dependingOnTask, type: GenerateHelperTask) {
                    manifestFile = manifestOutFile
                    helperPackage = project.android.defaultConfig.applicationId + ".helper"
                    helperClassName = 'PermissionsHelper'
                    outputDir = outputHelper
                }
                variant.registerJavaGeneratingTask(task, outputHelper)
            }
        }
    }
}

