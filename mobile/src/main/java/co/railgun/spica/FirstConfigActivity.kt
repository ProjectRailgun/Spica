package co.railgun.spica

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import co.railgun.common.activity.BaseActivity
import co.railgun.common.cache.SpicaPreferences
import co.railgun.spica.api.SpicaClient
import co.railgun.spica.api.function.user.login
import co.railgun.spica.api.model.ActionResponse
import co.railgun.spica.databinding.ActivityFirstConfigBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class FirstConfigActivity : BaseActivity() {

    private val binding: ActivityFirstConfigBinding by lazy {
        ActivityFirstConfigBinding.inflate(layoutInflater)
    }

    private val toast by lazy { Toast.makeText(applicationContext, "", Toast.LENGTH_LONG) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.floatingActionButton.setOnClickListener { login() }
    }

    private fun login() {
        lifecycleScope.launch {
            toast.setText(getString(R.string.connecting))
            toast.show()
            when (
                val loginResponse = SpicaClient.User.login(
                    name = binding.user.text.toString(),
                    password = binding.pw.text.toString()
                )
            ) {
                is ActionResponse.Ok -> {
                    SpicaPreferences.saveUsername(binding.user.text.toString())
                    startActivity(Intent(this@FirstConfigActivity, HomeActivity::class.java))
                    Firebase.analytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
                        param(FirebaseAnalytics.Param.METHOD, "origin")
                    }
                    toast.cancel()
                    finish()
                }
                is ActionResponse.Message -> {
                    toast.setText(loginResponse.message)
                    toast.show()
                }
            }
        }
    }
}
