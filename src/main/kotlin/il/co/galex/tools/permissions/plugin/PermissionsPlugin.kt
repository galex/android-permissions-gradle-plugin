package il.co.galex.tools.permissions.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.api.BaseVariantOutput
import il.co.galex.tools.permissions.model.PermissionsExtension
import il.co.galex.tools.permissions.task.GenerateHelperTask
import org.gradle.api.DomainObjectSet
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

open class PermissionsPlugin : Plugin<Project> {

    override fun apply(target: Project?) {

        target?.let {
            with(it) {

                extensions.create("permissions", PermissionsExtension::class.java)
                val hasApp = plugins.hasPlugin(AppPlugin::class.java)
                val hasLib = plugins.hasPlugin(LibraryPlugin::class.java)

                if (!hasApp && !hasLib) {
                    throw IllegalStateException("'com.android.application' or 'com.android.library' plugin required.")
                }

                val variants: DomainObjectSet<out BaseVariant> = if (hasApp) {
                    extensions.findByType(AppExtension::class.java)!!.applicationVariants
                } else {
                    extensions.findByType(LibraryExtension::class.java)!!.libraryVariants
                }

                variants.all { variant: BaseVariant ->
                    variant.outputs.all { output: BaseVariantOutput ->

                        val taskName = "generate${variant.name.capitalize()}PermissionsHelper"

                        val manifestOutFile = File(output.processManifest.manifestOutputDirectory.absolutePath + "/AndroidManifest.xml")
                        val helperPackage = variant.applicationId + ".helper"
                        val helperClassName = "PermissionsHelper"
                        val outputHelper = project.file("${project.buildDir}/generated/source/permissions/${variant.name}/")

                        println("outputHelper = $outputHelper")

                        val generateHelperTask = tasks.create(taskName, GenerateHelperTask::class.java) {
                            it.manifestFile = manifestOutFile
                            it.helperPackage = helperPackage
                            it.helperClassName = helperClassName
                            it.outputDir = outputHelper
                        }

                        // depends on the
                        val dependingOnTask = "process${variant.name.capitalize()}Manifest"
                        generateHelperTask.dependsOn.add(dependingOnTask)

                        // register this task as generation code in the output helper folder
                        variant.registerJavaGeneratingTask(generateHelperTask, outputHelper)
                    }
                }
            }
        }
    }
}