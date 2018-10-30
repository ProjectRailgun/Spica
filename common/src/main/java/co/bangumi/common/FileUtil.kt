package co.bangumi.common

import android.os.Environment

class FileUtil {
    companion object {
        fun checkOrCreateFolder(folder: String): Boolean {
            val folder = Environment.getExternalStoragePublicDirectory(folder)
            return if (folder.exists() && folder.isDirectory) true else folder.mkdirs()
        }
    }
}

