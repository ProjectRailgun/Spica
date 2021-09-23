package co.railgun.spica

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import co.railgun.common.PackageUtil
import co.railgun.common.api.ApiClient
import co.railgun.common.cache.SpicaPreferences
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks


/**
 * Created by roya on 2017/5/26.
 */
class StartPaddingActivity : BaseThemeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isConfigured = SpicaPreferences.isConfigured
        val intent = intent
        if (isConfigured && intent.action == Intent.ACTION_VIEW) {
            var url = intent.dataString
            FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(
                    this
                ) { pendingDynamicLinkData ->
                    if (pendingDynamicLinkData != null) {
                        url = pendingDynamicLinkData.link.toString()
                        if (pendingDynamicLinkData.minimumAppVersion
                            > PackageUtil.getVersionCode(this)
                        ) {
                            startActivity(pendingDynamicLinkData.getUpdateAppIntent(this))
                        }
                    }

                    if (!PackageUtil.isActivityAlive(this, Constant.HOME_ACTIVITY_NAME))
                        startActivity(Intent(this, HomeActivity::class.java))

                    ApiClient.getInstance()
                        .getBangumiDetail(url!!.substring(url!!.lastIndexOf("/") + 1))
                        .withLifecycle()
                        .subscribe({
                            this.startActivity(DetailActivity.intent(this, it.getData()))
                            finish()
                        }, {
                            showToast(getString(R.string.empty), Toast.LENGTH_SHORT)
                            finish()
                        })
                }

        } else {
            startActivity(
                Intent(
                    this,
                    if (isConfigured) HomeActivity::class.java
                    else FirstConfigActivity::class.java
                )
            )
            finish()
        }
    }


}
