package co.bangumi.Cassiopeia

import android.app.Application

import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker

/**
 * This is a subclass of [Application] used to provide shared objects for this app, such as
 * the [Tracker].
 */
class AnalyticsApplication : Application() {

    /**
     * Gets the default [Tracker] for this [Application].
     * @return tracker
     */
    // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
    val defaultTracker: Tracker
        @Synchronized get() {
            if (sTracker == null) {
                sTracker = sAnalytics!!.newTracker(R.xml.global_tracker)
            }

            return sTracker!!
        }

    override fun onCreate() {
        super.onCreate()

        sAnalytics = GoogleAnalytics.getInstance(this)
    }

    companion object {

        private var sAnalytics: GoogleAnalytics? = null
        private var sTracker: Tracker? = null
    }
}