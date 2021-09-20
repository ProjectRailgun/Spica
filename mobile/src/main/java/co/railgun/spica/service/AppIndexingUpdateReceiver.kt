package co.railgun.spica.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.firebase.appindexing.FirebaseAppIndex

class AppIndexingUpdateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null && FirebaseAppIndex.ACTION_UPDATE_INDEX == intent.action) {
            AppIndexingUpdateService.enqueueWork(context)
        }
    }
}