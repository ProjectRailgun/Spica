package co.bangumi.Cassiopeia

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.AppCompatSpinner
import android.widget.EditText
import android.widget.Toast
import co.bangumi.common.api.LoginRequest
import co.bangumi.common.cache.CygnusPreferences
import com.google.firebase.analytics.FirebaseAnalytics
import retrofit2.HttpException


class FirstConfigActivity : co.bangumi.common.activity.BaseActivity() {

    private val spinner by lazy { findViewById<AppCompatSpinner>(R.id.spinner) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_config)

        /**
        val sp = ArrayAdapter.createFromResource(this,
                co.bangumi.Cygnus.R.array.array_link_type, co.bangumi.Cygnus.R.layout.spinner_item)
        sp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = sp

        val textServer = findViewById(co.bangumi.Cygnus.R.id.server) as EditText
        textServer.setText(CygnusPreferences.getServer(), TextView.BufferType.EDITABLE)
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
            host.append("https://bangumi.co/")

            /**
            if (!domain.endsWith("/"))
                host.append("/")
            **/

            toast.setText(getString(R.string.connecting))
            toast.show()

            co.bangumi.common.api.ApiClient.init(this, host.toString())
            co.bangumi.common.api.ApiClient.getInstance().login(LoginRequest(textUser.text.toString(), textPw.text.toString(), true))
                    .withLifecycle()
                    .subscribe({
                        CygnusPreferences.setServer(host.toString())
                        CygnusPreferences.setUsername(textUser.text.toString())
                        startActivity(Intent(this, HomeActivity::class.java))
                        val bundle = Bundle()
                        bundle.putString(FirebaseAnalytics.Param.METHOD, "origin")
                        FirebaseAnalytics.getInstance(this)
                            .logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
                        toast.cancel()
                        finish()
                    }, {
                        var errorMessage = getString(R.string.network_error)

                        if (it is HttpException) {
                            val body = it.response().errorBody()
                            val message = body?.let { it1 ->
                                co.bangumi.common.api.ApiClient.converterErrorBody(it1)
                            }

                            if (message?.message() != null) {
                                errorMessage = message.message()
                            }
                        }

                        toast.setText(errorMessage)
                        toast.show()
                    })
        }
    }
}
