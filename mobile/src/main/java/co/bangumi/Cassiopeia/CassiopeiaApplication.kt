package co.bangumi.Cassiopeia

import android.app.Application
import android.content.Context
import android.content.Intent
import co.bangumi.common.cache.CygnusPreferences
import co.bangumi.common.cache.PreferencesUtil
import com.google.firebase.appindexing.FirebaseAppIndex


class CassiopeiaApplication : Application() {

    companion object {

        fun logout(context: Context) {
            PreferencesUtil.getInstance().clear()
            co.bangumi.common.api.ApiClient.deinit()
            FirebaseAppIndex.getInstance().removeAll()
            val i = context.applicationContext.packageManager.getLaunchIntentForPackage(context.applicationContext.packageName)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(i)
        }
    }

    override fun onCreate() {
        super.onCreate()

        PreferencesUtil.init(this)
        if (CygnusPreferences.configured()) {
            co.bangumi.common.api.ApiClient.init(this, CygnusPreferences.getServer())
        }
    }
}