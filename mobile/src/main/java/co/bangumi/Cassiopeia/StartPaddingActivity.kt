package co.bangumi.Cassiopeia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import co.bangumi.common.PackageUtil
import co.bangumi.common.api.ApiClient
import co.bangumi.common.cache.CygnusPreferences
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks


/**
 * Created by roya on 2017/5/26.
 */
class StartPaddingActivity : co.bangumi.common.activity.BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isConfigured = CygnusPreferences.configured()
        val intent = intent;
        if (isConfigured && intent.action == Intent.ACTION_VIEW) {
            val url = intent.dataString
            FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(
                    this
                ) { pendingDynamicLinkData ->
                    var deepLink: Uri? = null
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData!!.link
                        if (pendingDynamicLinkData.minimumAppVersion
                            > PackageUtil.getVersionCode(this)
                        ) {
                            startActivity(pendingDynamicLinkData.getUpdateAppIntent(this))
                        }
                    }

                    if (!PackageUtil.isActivityAlive(this, Constant.HOME_ACTIVITY_NAME))
                        startActivity(Intent(this, HomeActivity::class.java))

                    ApiClient.getInstance()
                        .getBangumiDetail(url.substring(url.lastIndexOf("/") + 1))
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