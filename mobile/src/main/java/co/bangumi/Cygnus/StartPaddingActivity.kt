package co.bangumi.Cygnus

import android.content.Intent
import android.os.Bundle
import co.bangumi.common.activity.BaseActivity
import co.bangumi.common.cache.CygnusPreferences

/**
 * Created by roya on 2017/5/26.
 */
class StartPaddingActivity : co.bangumi.common.activity.BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this,
                if (CygnusPreferences.configured()) HomeActivity::class.java
                else FirstConfigActivity::class.java))
//        startActivity(PlayerActivity.intent(this, "224","24ref","faf"))
        finish()
    }
}