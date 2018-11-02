package co.bangumi.Cassiopeia

import android.app.Application
import android.content.Context
import android.content.Intent
import co.bangumi.common.cache.CygnusPreferences
import co.bangumi.common.cache.PreferencesUtil
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import com.google.firebase.appindexing.FirebaseAppIndex


/**
 * This is a subclass of [Application] used to provide shared objects for this app, such as
 * the [Tracker].
 */
class AnalyticsApplication : Application() {

    companion object {

        private var sAnalytics: GoogleAnalytics? = null
        private var sTracker: Tracker? = null

        fun logout(context: Context) {
            PreferencesUtil.getInstance().clear()
            co.bangumi.common.api.ApiClient.deinit()
            FirebaseAppIndex.getInstance().removeAll();
            val i = context.applicationContext.packageManager.getLaunchIntentForPackage(context.applicationContext.packageName)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(i)
        }
    }

    override fun onCreate() {
        super.onCreate()

        sAnalytics = GoogleAnalytics.getInstance(this)

        PreferencesUtil.init(this)
        if (CygnusPreferences.configured()) {
            co.bangumi.common.api.ApiClient.init(this, CygnusPreferences.getServer())
        }
    }

    /**
     * Gets the default [Tracker] for this [Application].
     * @return tracker
     */
    val defaultTracker: Tracker
        @Synchronized get() {
            if (sTracker == null) {
                sTracker = sAnalytics!!.newTracker(R.xml.global_tracker)
            }

            return sTracker!!
        }

    /**
     * Record a screen view hit for the visible view displayed
     */
    public fun sendScreenTrack(name: String) {
        defaultTracker.setScreenName(name)
        defaultTracker.send(HitBuilders.ScreenViewBuilder().build())
    }
}