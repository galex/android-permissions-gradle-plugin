package il.co.galex.tools.permissions.task

import groovy.text.GStringTemplateEngine
import il.co.galex.tools.permissions.model.DangerousPermission
import il.co.galex.tools.permissions.model.PermissionsExtension
import il.co.galex.tools.permissions.util.SUFFIX
import il.co.galex.tools.permissions.util.file
import il.co.galex.tools.permissions.util.requiredPermissions
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.PrintWriter

open class GenerateHelperTask : DefaultTask() {

    companion object {
        private val HELPER_TEMPLATE_PATH = "/templates/PermissionsHelper.java.template"
    }

    @Input
    var manifestFile: File? = null

    @Input
    var helperPackage: String? = null

    @Input
    var helperClassName: String? = null

    @Input
    var outputDir: File? = null


    @Suppress("unused")
    @TaskAction
    fun generateHelper() {

        val extension: PermissionsExtension? = project.extensions.findByType(PermissionsExtension::class.java)

        extension?.let {

            val classPackage: String? = extension.helperPackage ?: helperPackage
            val className: String? = extension.helperClassName ?: helperClassName
            val permissions: List<DangerousPermission>? = manifestFile?.requiredPermissions()

            val tempFile: File = javaClass.getResource(HELPER_TEMPLATE_PATH).toURI().file
            val binding: Map<String, Any?> = mapOf(
                    "permissions" to permissions,
                    "helperPackage" to helperPackage,
                    "helperClassName" to className)

            val engine = GStringTemplateEngine()
            val template = engine.createTemplate(tempFile).make(binding)
            val generatedTemplate = template.toString()

            // write this string to the correct path of file
            val fileDirPath: String = outputDir?.absolutePath + "/" + classPackage?.replace('.', '/') + "/"
            val fileDir = File(fileDirPath)
            fileDir.mkdirs()
            val finalFile = File(fileDirPath + className + SUFFIX)
            //println finalFile
            finalFile.createNewFile()

            // write into the final file the generated template
            val out = PrintWriter(finalFile)
            out.println(generatedTemplate)
            out.close()
        }
    }
}