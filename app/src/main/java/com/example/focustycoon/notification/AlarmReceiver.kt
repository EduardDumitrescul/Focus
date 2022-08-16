package com.example.focustycoon.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.focustycoon.MainActivity
import com.example.focustycoon.R

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if((Intent.ACTION_BOOT_COMPLETED) == intent?.action) {

        }
        else {
            // don't send notification if app is running
            if(MainActivity.isActive) {
                return
            }

            assert(context != null)
            val builder = NotificationCompat.Builder(context!!, MainActivity.CHANNEL_ID )
                .setSmallIcon(R.drawable.coin_focus_48)
                .setContentTitle("Title")
                .setContentText("text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(1, builder.build())
            }

        }
    }



}