package il.co.galex.permissions.task

import il.co.galex.permissions.model.DangerousPermission
import il.co.galex.permissions.util.FilterUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * Created by galex on 06/02/16.
 */
class GenerateHelperTask extends DefaultTask {

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

        println FilterUtils.getRequiredPermissions(manifestFile)

    }
}
