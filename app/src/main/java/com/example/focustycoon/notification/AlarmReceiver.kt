package com.example.focustycoon.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.focustycoon.MainActivity
import com.example.focustycoon.MainApplication
import com.example.focustycoon.R

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if((Intent.ACTION_BOOT_COMPLETED) == intent?.action) {

        }
        else {
            // don't send notification if app is running
            if(MainApplication.isActive) {
                return
            }

            val contentIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
            }

            val contentPendingIntent = PendingIntent.getActivity(context, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            assert(context != null)
            val builder = NotificationCompat.Builder(context!!, MainActivity.CHANNEL_ID )
                .setSmallIcon(R.drawable.focus_icon_notif_48px)
                .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.focus_icon_notif_48px))
                .setContentTitle("You just completed your focused session")
                .setContentText("Come back to get your rewards!")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(1, builder.build())
            }

        }
    }



}