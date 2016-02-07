package il.co.galex.permissions.util

import org.apache.commons.io.IOUtils
import org.gradle.api.DefaultTask

import java.util.jar.JarFile

/**
 * Created by galex on 07/02/16.
 */
class FileUtils {

    public static final String PREFIX = "PermissionsHelper";
    public static final String SUFFIX = ".java";

    private static InputStream getInputStream(URI uri){
        //println "uri = " + uri
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

    private static File getResourceFile(InputStream inputStream) throws IOException {

        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        try {
            FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copy(inputStream, out);
        }
        catch (Exception e){}
        return tempFile
    }

    public static File getFile(URI uri){
        return getResourceFile(getInputStream(uri))
    }
}
