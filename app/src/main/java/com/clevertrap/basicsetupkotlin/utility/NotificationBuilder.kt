package com.clevertrap.basicsetupkotlin.utility


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.clevertrap.basicsetupkotlin.R


class NotificationBuilder {

    private var notification: Notification? = null
    var context: Context? = null
    var title: String? = null
    var message: String? = null
    var pedingIntent: PendingIntent? = null
        private set
    var icon = 0
    var isVibrateOn = false

    fun setPendingIntent(pedingIntent: PendingIntent) {
        this.pedingIntent = pedingIntent
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun build(): Notification? {
        if (AndroidVersionUtil.isBeforeOreo) {

            val defaultFlag = if (isVibrateOn) Notification.DEFAULT_VIBRATE or Notification.DEFAULT_SOUND else Notification.DEFAULT_SOUND

            val bigTextStyle = NotificationCompat.BigTextStyle()
            val inboxStyle = NotificationCompat.InboxStyle()
            inboxStyle.addLine(message)
            bigTextStyle.bigText(message)
            val builder = NotificationCompat.Builder(context!!, "NOTIFICATION_CHANNEL_GLOBAL")
                    .setContentTitle(title)
                    .setContentText(message)
                    .setStyle(bigTextStyle)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(if (icon > 0) icon else R.mipmap.ic_launcher_round)
                    .setDefaults(defaultFlag)
                    .setContentIntent(pedingIntent)
                    .setAutoCancel(true)


            notification = builder.build()
        } else {

            val charSeq = "Greenforce"
            val notificationChannelId = "NOTIFICATION_CHANNEL_GLOBAL"

            val channel = NotificationChannel(notificationChannelId,
                    charSeq,
                    NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(isVibrateOn)
            channel.vibrationPattern = longArrayOf(500, 500)

            (context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)

            val builder = Notification.Builder(context, "NOTIFICATION_CHANNEL_GLOBAL")
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(if (icon > 0) icon else R.mipmap.ic_launcher_round)
                    .setContentIntent(pedingIntent)
                    .setChannelId(notificationChannelId)
                    .setAutoCancel(true)

            notification = builder.build()


        }

        return notification
    }
}
