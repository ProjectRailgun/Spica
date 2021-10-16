package co.railgun.common.cache

/**
 * Created by roya on 2017/5/26.
 */
object SpicaPreferences {

    private const val KEY_MP_USERNAME = "SpicaPreferences:KEY_MP_USERNAME"

    val isConfigured: Boolean
        get() = PreferencesUtil.getInstance().getString(KEY_MP_USERNAME, "").isNotBlank()

    fun saveUsername(server: String) {
        PreferencesUtil.getInstance().putString(KEY_MP_USERNAME, server)
    }
}
