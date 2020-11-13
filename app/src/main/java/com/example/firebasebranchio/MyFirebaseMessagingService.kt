package com.example.firebasebranchio

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = javaClass.simpleName
    override fun onNewToken(token : String) {
        super.onNewToken(token)
        Log.e(MY_TAG, "$TAG : onNewToken: $token")
    }

    override fun onMessageReceived(msg: RemoteMessage) {
        super.onMessageReceived(msg)
        val title = msg.data["title"]
        val message = msg.data["message"]
        val intent = Intent(this, MainActivity::class.java ).also {}
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel = "channel"
            val channelName = MY_TAG
            val notiChannel = getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            val channelMessage = NotificationChannel(
                channel,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channelMessage.description = "Notification"
            channelMessage.enableLights(true)
            channelMessage.enableVibration(true)
            channelMessage.setShowBadge(false)
            channelMessage.vibrationPattern = longArrayOf(1000, 1000)
            notiChannel.createNotificationChannel(channelMessage)

            val builder = NotificationCompat.Builder(this, channel)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setChannelId(channel)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(9999, builder.build())
        } else {
            val builder = NotificationCompat.Builder(this, "")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(9999, builder.build())
        }

    }
}
