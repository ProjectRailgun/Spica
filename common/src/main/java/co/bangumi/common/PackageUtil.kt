package co.bangumi.common

import android.app.ActivityManager
import android.content.Context

class PackageUtil {
    companion object {
        fun getVersionCode(context: Context): Int {
            return context.getPackageManager()
                .getPackageInfo(context.packageName, 0).versionCode
        }

        fun isActivityAlive(context: Context, activityName: String?): Boolean {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val list = am.getRunningTasks(100)
            for (info in list) {
                if (info.topActivity.className == activityName
                    || info.baseActivity.className == activityName
                ) {
                    return true
                }
            }
            return false
        }
    }
}