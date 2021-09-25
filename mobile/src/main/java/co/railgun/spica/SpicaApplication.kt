package co.railgun.spica

import android.app.Application
import android.content.Context
import android.content.Intent
import co.railgun.api.bgmrip.BgmRipClient
import co.railgun.api.bgmrip.function.initialize
import co.railgun.common.cache.PreferencesUtil
import com.google.firebase.appindexing.FirebaseAppIndex
import me.omico.cryonics.cryonicsCookiesDataStore

@Suppress("unused")
class SpicaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferencesUtil.init(this)
        BgmRipClient.initialize(this)
    }

    companion object {

        fun logout(context: Context) {
            PreferencesUtil.getInstance().clear()
            context.cryonicsCookiesDataStore.clear()
            FirebaseAppIndex.getInstance(context).removeAll()
            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)
        }
    }
}
