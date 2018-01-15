package il.co.galex.tools.permissions.util

import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URI
import java.util.jar.JarFile

/**
 * File Utility class for file related stuff.
 *
 * @author Alexander Gherschon
 */

private const val PREFIX = "PermissionsHelper"
private const val SUFFIX = ".java"

private val URI.inputStream: InputStream
    get() {
        val params = toString().split('!')
        val jarPath = params[0]
        val resourcePath = params[1].substring(1)
        val jarFile = JarFile(jarPath.split(':')[2])
        val jarEntry = jarFile.getEntry(resourcePath)
        return jarFile.getInputStream(jarEntry)
    }

private val InputStream.resourceFile: File
    get() {
        val tempFile = File.createTempFile(PREFIX, SUFFIX)
        tempFile.deleteOnExit()
        val out = FileOutputStream(tempFile)
        IOUtils.copy(this, out)
        return tempFile
    }

public val URI.file: File
    get() = this.inputStream.resourceFile
