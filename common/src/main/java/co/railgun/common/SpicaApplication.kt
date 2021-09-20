package co.railgun.common

import android.app.Application
import android.content.Context
import android.content.Intent
import co.railgun.common.cache.SpicaPreferences
import co.railgun.common.cache.PreferencesUtil
import com.google.firebase.appindexing.FirebaseAppIndex


/**
 * Created by roya on 2017/5/24.
 */

class SpicaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferencesUtil.init(this)
        if (SpicaPreferences.configured()) {
            co.railgun.common.api.ApiClient.init(this, SpicaPreferences.getServer())
        }
    }

    companion object {
        fun logout(context: Context) {
            PreferencesUtil.getInstance().clear()
            co.railgun.common.api.ApiClient.deinit()
            FirebaseAppIndex.getInstance(context).removeAll()
            val i = context.applicationContext.packageManager.getLaunchIntentForPackage(context.applicationContext.packageName)
            i?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(i)
        }
    }
}