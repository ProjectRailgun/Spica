package co.railgun.common

import android.app.ActivityManager
import android.content.Context

object PackageUtil {

    fun getVersionCode(context: Context): Int =
        context.packageManager.getPackageInfo(context.packageName, 0).versionCode

    fun isActivityAlive(context: Context, activityName: String): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val list = am.getRunningTasks(100)
        for (info in list) {
            if (info.topActivity?.className == activityName
                || info.baseActivity?.className == activityName
            ) {
                return true
            }
        }
        return false
    }
}
