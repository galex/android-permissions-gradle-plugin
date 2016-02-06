package il.co.galex.permissions.task

import groovy.text.GStringTemplateEngine
import groovy.text.StreamingTemplateEngine
import il.co.galex.permissions.model.DangerousPermission
import il.co.galex.permissions.util.FilterUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

import java.util.jar.JarFile

/**
 * Created by galex on 06/02/16.
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
    def generateHelper() {

        println "GenerateHelper manifestFile = " + manifestFile
        //println "GenerateHelper requiredPermissions = " + requiredPermissions
        println "GenerateHelper helperPackage = " + helperPackage
        println "GenerateHelper helperClassName = " + helperClassName
        println "GenerateHelper outputDir = " + outputDir

        def permissions = FilterUtils.getRequiredPermissions(manifestFile)
        println permissions

        def path = '/templates/PermissionsHelper.java.template';
        File tempFile = getResourceFile(getResource(path))
        //println tempFile

        def binding = [
                permissions : permissions,
                helperPackage : helperPackage
        ]

        def engine = new GStringTemplateEngine()
        def template = engine.createTemplate(tempFile).make(binding)
        def generatedTemplate = template.toString()
        println generatedTemplate

        // write this string to the correct path of file

        String fileDirPath = outputDir.getAbsolutePath() + "/" + helperPackage.replace('.', '/') + "/"
        println fileDirPath

        File fileDir = new File(fileDirPath)
        fileDir.mkdirs();

        File finalFile = new File(fileDirPath + helperClassName + ".java")
        println finalFile

        finalFile.createNewFile();

        PrintWriter out = new PrintWriter(finalFile);
        out.println(generatedTemplate)
        out.close()
    }


    private InputStream getResource(String path){

        URI uri = this.getClass().getResource(path).toURI()
        println "uri = " + uri
        // to read the resource file at runtime, we have no other choice than to manage the jar file itself
        def params = uri.toString().split('!')
        def jarPath = params[0]
        def resourcePath = params[1].substring(1)
        println resourcePath
        def jarFile = new JarFile(jarPath.split(':')[2])
        println jarFile
        def jarEntry = jarFile.getEntry(resourcePath)
        return jarFile.getInputStream(jarEntry)
    }

    public static File getResourceFile(InputStream inputStream) throws IOException {

        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        try {
            FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copy(inputStream, out);
        }
        catch (Exception e){}
        return tempFile
    }
}
