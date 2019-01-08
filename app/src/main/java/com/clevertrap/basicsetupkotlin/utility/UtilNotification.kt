package com.fidelitservices.brownstone.utility


import android.app.Notification
import android.app.NotificationManager
import android.content.Context

object UtilNotification {

    fun showNotification(context: Context, notification: Notification, id: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id, notification)
    }

}
