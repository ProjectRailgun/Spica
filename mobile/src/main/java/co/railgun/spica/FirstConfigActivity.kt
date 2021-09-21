package co.railgun.spica

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import co.railgun.common.api.LoginRequest
import co.railgun.common.cache.SpicaPreferences
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import retrofit2.HttpException


class FirstConfigActivity : co.railgun.common.activity.BaseActivity() {

    private val spinner by lazy { findViewById<AppCompatSpinner>(R.id.spinner) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_config)

        /**
        val sp = ArrayAdapter.createFromResource(this,
                co.railgun.spica.R.array.array_link_type, co.railgun.spica.R.layout.spinner_item)
        sp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = sp

        val textServer = findViewById(co.railgun.spica.R.id.server) as EditText
        textServer.setText(SpicaPreferences.getServer(), TextView.BufferType.EDITABLE)
        **/

        val textUser = findViewById<EditText>(R.id.user)
        val textPw = findViewById<EditText>(R.id.pw)


        val toast = Toast.makeText(this, getString(R.string.connecting), Toast.LENGTH_LONG)

        (findViewById<FloatingActionButton>(R.id.floatingActionButton)).setOnClickListener {
            /**
            val host = StringBuilder()
            val domain = textServer.text.toString()

            if (domain.isEmpty()) {
                showToast("Please enter domain")
                return@setOnClickListener
            }

            val isHttps = spinner.selectedItemPosition == 1
            host.append(if (isHttps) "https://" else "http://")
            host.append(domain)
            **/

            val host = StringBuilder()
            host.append("https://bgm.rip/")

            /**
            if (!domain.endsWith("/"))
                host.append("/")
            **/

            toast.setText(getString(R.string.connecting))
            toast.show()

            co.railgun.common.api.ApiClient.init(this, host.toString())
            co.railgun.common.api.ApiClient.getInstance().login(LoginRequest(textUser.text.toString(), textPw.text.toString(), true))
                    .withLifecycle()
                    .subscribe({
                        SpicaPreferences.setServer(host.toString())
                        SpicaPreferences.setUsername(textUser.text.toString())
                        startActivity(Intent(this, HomeActivity::class.java))
                        Firebase.analytics.logEvent(FirebaseAnalytics.Event.LOGIN){
                            param(FirebaseAnalytics.Param.METHOD, "origin")
                        }
                        toast.cancel()
                        finish()
                    }, {
                        var errorMessage = getString(R.string.network_error)

                        if (it is HttpException) {
                            val body = it.response().errorBody()
                            val message = body?.let { it1 ->
                                co.railgun.common.api.ApiClient.converterErrorBody(it1)
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
