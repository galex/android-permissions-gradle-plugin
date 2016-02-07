package il.co.galex.permissions.task

import groovy.text.GStringTemplateEngine
import groovy.text.StreamingTemplateEngine
import il.co.galex.permissions.model.DangerousPermission
import il.co.galex.permissions.util.FileUtils
import il.co.galex.permissions.util.FilterUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

import java.util.jar.JarFile

/**
 * PermissionsHelper class generation task.
 * This tasks receives all the parameters necessary to generate the PermissionsHelper class.
 *
 * @author Alexander Gherschon
 */
class GenerateHelperTask extends DefaultTask {

    public static final String PREFIX = "stream2file";
    public static final String SUFFIX = ".tmp";

    //@Input
    //List<DangerousPermission> requiredPermissions

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

        //println "config permissions.helperPackage ${project.permissions.helperPackage}"
        //println "config permissions.helperClassName ${project.permissions.helperClassName}"

        //println "GenerateHelper manifestFile = " + manifestFile
        //println "GenerateHelper requiredPermissions = " + requiredPermissions

        def classPackage = project.permissions.helperPackage != null ? project.permissions.helperPackage : helperPackage
        def className = project.permissions.helperClassName != null ? project.permissions.helperClassName : helperClassName

        //println "GenerateHelper helperPackage = " + classPackage
        //println "GenerateHelper helperClassName = " + className
        //println "GenerateHelper outputDir = " + outputDir

        def permissions = FilterUtils.getRequiredPermissions(manifestFile)
        //println permissions

        def path = '/templates/PermissionsHelper.java.template';
        URI uri = this.getClass().getResource(path).toURI()
        File tempFile = FileUtils.getFile(uri)
        //println tempFile

        def binding = [
                permissions : permissions,
                helperPackage : classPackage,
                helperClassName : className
        ]

        def engine = new GStringTemplateEngine()
        def template = engine.createTemplate(tempFile).make(binding)
        def generatedTemplate = template.toString()
        //println generatedTemplate

        // write this string to the correct path of file

        String fileDirPath = outputDir.getAbsolutePath() + "/" + classPackage.replace('.', '/') + "/"
        //println fileDirPath

        File fileDir = new File(fileDirPath)
        fileDir.mkdirs();
        File finalFile = new File(fileDirPath + className + FileUtils.SUFFIX)
        println finalFile

        finalFile.createNewFile();

        PrintWriter out = new PrintWriter(finalFile);
        out.println(generatedTemplate)
        out.close()
    }
}
