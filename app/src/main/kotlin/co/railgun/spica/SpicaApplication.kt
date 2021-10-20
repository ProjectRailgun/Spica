package co.railgun.spica

import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import me.omico.xero.core.CoreApplication

class SpicaApplication : CoreApplication() {

    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
    }
}
