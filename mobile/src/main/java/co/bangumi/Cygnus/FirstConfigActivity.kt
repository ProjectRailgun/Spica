package co.bangumi.Cygnus

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatSpinner
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import co.bangumi.common.activity.BaseActivity
import co.bangumi.common.api.ApiClient
import co.bangumi.common.api.LoginRequest
import co.bangumi.common.cache.CygnusPreferences
import retrofit2.HttpException

class FirstConfigActivity : co.bangumi.common.activity.BaseActivity() {

    private val spinner by lazy { findViewById(co.bangumi.Cygnus.R.id.spinner) as AppCompatSpinner }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(co.bangumi.Cygnus.R.layout.activity_first_config)

        /**
        val sp = ArrayAdapter.createFromResource(this,
                co.bangumi.Cygnus.R.array.array_link_type, co.bangumi.Cygnus.R.layout.spinner_item)
        sp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = sp

        val textServer = findViewById(co.bangumi.Cygnus.R.id.server) as EditText
        textServer.setText(CygnusPreferences.getServer(), TextView.BufferType.EDITABLE)
        **/

        val textUser = findViewById(co.bangumi.Cygnus.R.id.user) as EditText
        val textPw = findViewById(co.bangumi.Cygnus.R.id.pw) as EditText


        val toast = Toast.makeText(this, getString(co.bangumi.Cygnus.R.string.connecting), Toast.LENGTH_LONG)

        findViewById(co.bangumi.Cygnus.R.id.floatingActionButton).setOnClickListener {
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

            toast.setText(getString(co.bangumi.Cygnus.R.string.connecting))
            toast.show()

            co.bangumi.common.api.ApiClient.init(this, host.toString())
            co.bangumi.common.api.ApiClient.getInstance().login(LoginRequest(textUser.text.toString(), textPw.text.toString(), true))
                    .withLifecycle()
                    .subscribe({
                        CygnusPreferences.setServer(host.toString())
                        CygnusPreferences.setUsername(textUser.text.toString())
                        startActivity(Intent(this, HomeActivity::class.java))
                        toast.cancel()
                        finish()
                    }, {
                        var errorMessage = getString(co.bangumi.Cygnus.R.string.network_error)

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
