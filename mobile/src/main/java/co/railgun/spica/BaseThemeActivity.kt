package co.railgun.spica

import android.os.Bundle

import co.railgun.common.activity.BaseActivity
import co.railgun.common.cache.PreferencesUtil

abstract class BaseThemeActivity : BaseActivity() {

    protected val isWhiteTheme: Boolean
        get() = PreferencesUtil.getInstance().getBoolean("whiteTheme", false)!!

    open fun themeWhite(): Int {
        return R.style.AppThemeWhite
    }

    open fun themeStand(): Int {
        return R.style.AppTheme
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(if (isWhiteTheme) themeWhite() else themeStand())
        super.onCreate(savedInstanceState)
    }

    protected fun themeChanged() {
        PreferencesUtil.getInstance().putBoolean("whiteTheme", !isWhiteTheme)
        recreate()
    }
}
