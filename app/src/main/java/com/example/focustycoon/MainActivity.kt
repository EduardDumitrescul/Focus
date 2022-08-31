package com.example.focustycoon

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.focustycoon.storage.UserDataSource
import javax.inject.Inject


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    companion object {
        var isActive: Boolean = false
        const val CHANNEL_ID: String = "channel 2"
    }

    @Inject
    lateinit var userDataSource: UserDataSource

    @Inject
    lateinit var soundService: SoundService


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            channel.setSound(
                Uri.parse(
                    "android.resource://"
                            + this.packageName + "/" + R.raw.finish_success
                ),
                attributes
            )
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun saveData() {
        userDataSource.saveData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()
        (application as MainApplication).appComponent.inject(this)
        isActive = true
    }

    override fun onResume() {
        super.onResume()
        isActive = true
    }

    override fun onPause() {
        super.onPause()
        userDataSource.saveData()
        isActive = false
    }

    override fun onStop() {
        super.onStop()
        userDataSource.saveData()
        isActive = false
    }
}