package il.co.galex.tools.permissions.task

import groovy.text.GStringTemplateEngine
import il.co.galex.tools.permissions.util.FileExtensionsKt

import il.co.galex.tools.permissions.util.FilterUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * PermissionsHelper class generation task.
 * This tasks receives all the parameters necessary to generate the PermissionsHelper class.
 *
 * @author Alexander Gherschon
 */
class GenerateHelperTask extends DefaultTask {

    private static final String HELPER_TEMPLATE_PATH = '/templates/PermissionsHelper.java.template'

    @Input
    File manifestFile

    @Input
    String helperPackage

    @Input
    String helperClassName

    @Input
    File outputDir

    @TaskAction
    def void generateHelper() {

        def classPackage = project.permissions.helperPackage != null ? project.permissions.helperPackage : helperPackage
        def className = project.permissions.helperClassName != null ? project.permissions.helperClassName : helperClassName
        def permissions = FilterUtils.getRequiredPermissions(manifestFile)

        URI uri = this.getClass().getResource(HELPER_TEMPLATE_PATH).toURI()
        File tempFile = FileExtensionsKt.getFile(uri)

        def binding = [
                permissions : permissions,
                helperPackage : classPackage,
                helperClassName : className
        ]

        def engine = new GStringTemplateEngine()
        def template = engine.createTemplate(tempFile).make(binding)
        def generatedTemplate = template.toString()

        // write this string to the correct path of file
        String fileDirPath = outputDir.getAbsolutePath() + "/" + classPackage.replace('.', '/') + "/"
        File fileDir = new File(fileDirPath)
        fileDir.mkdirs();
        File finalFile = new File(fileDirPath + className + FileUtils.SUFFIX)
        //println finalFile
        finalFile.createNewFile();

        // write into the final file the generated template
        PrintWriter out = new PrintWriter(finalFile);
        out.println(generatedTemplate)
        out.close()
    }
}
