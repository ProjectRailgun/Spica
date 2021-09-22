package co.railgun.spica

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import co.railgun.common.activity.BaseActivity
import co.railgun.common.api.ApiClient
import co.railgun.common.api.LoginRequest
import co.railgun.common.cache.SpicaPreferences
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import retrofit2.HttpException

class FirstConfigActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_config)

        val textUser = findViewById<EditText>(R.id.user)
        val textPw = findViewById<EditText>(R.id.pw)

        val toast = Toast.makeText(this, getString(R.string.connecting), Toast.LENGTH_LONG)

        (findViewById<FloatingActionButton>(R.id.floatingActionButton)).setOnClickListener {
            val host = "https://bgm.rip/"

            toast.setText(getString(R.string.connecting))
            toast.show()

            ApiClient.init(this, host)
            ApiClient.getInstance()
                .login(LoginRequest(textUser.text.toString(), textPw.text.toString(), true))
                .withLifecycle()
                .subscribe({
                    SpicaPreferences.setServer(host)
                    SpicaPreferences.setUsername(textUser.text.toString())
                    startActivity(Intent(this, HomeActivity::class.java))
                    Firebase.analytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
                        param(FirebaseAnalytics.Param.METHOD, "origin")
                    }
                    toast.cancel()
                    finish()
                }, {
                    var errorMessage = getString(R.string.network_error)

                    if (it is HttpException) {
                        val body = it.response()?.errorBody()
                        val message = body?.let { it1 ->
                            ApiClient.converterErrorBody(it1)
                        }

                        if (message?.message() != null) {
                            val messageContent = message.message()
                            if (messageContent is String) {
                                errorMessage = messageContent
                            }
                        }
                    }

                    toast.setText(errorMessage)
                    toast.show()
                })
        }
    }
}
