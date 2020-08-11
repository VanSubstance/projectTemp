package com.example.contest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService:FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d(TAG, "new Token: $p0")
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d(TAG, "From: ${p0?.from}")
        sendNotification(p0.getNotification()?.getTitle().toString(), p0.getNotification()?.getBody().toString());
    }

    private fun sendNotification(title:String,body: String) {
        val intent =Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channelId = getString(R.string.default_notification_channel_id)
        var notificationBuilder = NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable.icon_main_black)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent)

        var notificationManager: NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "fcm_default_channel",
                "fcm_default_channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0 /*알림ID */, notificationBuilder.build())

    }
    companion object {
        private val TAG = "FirebaseMessageService"
    }

}