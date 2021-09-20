package co.railgun.spica

import android.app.Application
import android.content.Context
import android.content.Intent
import co.railgun.common.api.ApiClient
import co.railgun.common.cache.PreferencesUtil
import co.railgun.common.cache.SpicaPreferences
import com.google.firebase.appindexing.FirebaseAppIndex

@Suppress("unused")
class SpicaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferencesUtil.init(this)
        if (SpicaPreferences.configured()) {
            ApiClient.init(this, SpicaPreferences.getServer())
        }
    }

    companion object {

        fun logout(context: Context) {
            PreferencesUtil.getInstance().clear()
            ApiClient.deinit()
            FirebaseAppIndex.getInstance(context).removeAll()
            val i = context.applicationContext.packageManager.getLaunchIntentForPackage(context.applicationContext.packageName)
            i?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(i)
        }
    }
}
