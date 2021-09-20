package co.railgun.common

import java.io.File

object FileUtil {

    fun isFileExist(file: File): Boolean =
        file.exists() && file.isFile
}
