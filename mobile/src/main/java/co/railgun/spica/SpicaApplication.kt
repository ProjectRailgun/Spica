package co.railgun.spica

import android.app.Application
import android.content.Context
import android.content.Intent
import co.railgun.common.cache.SpicaPreferences
import co.railgun.common.cache.PreferencesUtil
import com.google.firebase.appindexing.FirebaseAppIndex


class SpicaApplication : Application() {

    companion object {

        fun logout(context: Context) {
            PreferencesUtil.getInstance().clear()
            co.railgun.common.api.ApiClient.deinit()
            FirebaseAppIndex.getInstance(context).removeAll()
            val i = context.applicationContext.packageManager.getLaunchIntentForPackage(context.applicationContext.packageName)
            if (i != null) {
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            context.startActivity(i)
        }
    }

    override fun onCreate() {
        super.onCreate()

        PreferencesUtil.init(this)
        if (SpicaPreferences.configured()) {
            co.railgun.common.api.ApiClient.init(this, SpicaPreferences.getServer())
        }
    }
}