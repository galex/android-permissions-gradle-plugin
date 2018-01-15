package il.co.galex.tools.permissions.util

import il.co.galex.tools.permissions.model.DangerousPermission
import org.w3c.dom.Document
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory


fun File.requiredPermissions(): List<DangerousPermission> {

    val requiredPermissionList = mutableListOf<DangerousPermission>()

    if (exists()) {

        val xmlDoc: Document? = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this)

        val usesPermissions: NodeList? = xmlDoc?.documentElement?.getElementsByTagName("uses-permission")

        usesPermissions?.let {

            val total = usesPermissions.length

            (0 until total).map { usesPermissions.item(it) }.forEach {

                val namedItem = it?.attributes?.getNamedItem("android:name")
                val permission = namedItem?.nodeValue?.substringAfterLast('.')
                val dangerousPermission = DangerousPermission.get(permission)
                if (dangerousPermission != null) requiredPermissionList.add(dangerousPermission)
            }
        }
    }
    return requiredPermissionList
}