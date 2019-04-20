package co.bangumi.Cassiopeia.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import co.bangumi.Cassiopeia.HomeActivity
import co.bangumi.Cassiopeia.R
import co.bangumi.common.BuildConfig
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    val TAG = "FCMService"
    val CHANNEL_ID = "other"
    val CHANNEL_NAME = "other"
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Log.d(TAG, "FCMService Create")
    }

    override fun onNewToken(s: String?) {
        if (BuildConfig.DEBUG) Log.d("FCM_TOKEN", s)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        sendNotification(remoteMessage!!)
        val intent = Intent(this@MyFirebaseMessagingService, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("message", remoteMessage.notification?.body)
        startActivity(intent)
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val mNotificationManager: NotificationManager =
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        val notificationBuilder: NotificationCompat.Builder
        notificationBuilder = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel =
                    NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            mNotificationManager.createNotificationChannel(notificationChannel)
            NotificationCompat.Builder(this, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(this)
        }
        notificationBuilder.setContentText(remoteMessage.notification?.body)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }
}