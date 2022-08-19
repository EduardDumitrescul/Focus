package com.example.focustycoon

import android.content.Context
import android.media.MediaPlayer
import com.example.focustycoon.settings.GlobalSettings
import javax.inject.Inject

class SoundService @Inject constructor(private val context: Context, private val globalSettings: GlobalSettings){
    private var mediaPlayer: MediaPlayer? = null
    private var activityRunning: Boolean = true

    fun setActivityStopped() {
        activityRunning = false
    }

    fun setActivityResumed() {
        activityRunning = true
    }

    fun playTimerTick1() {
        if(!activityRunning || !globalSettings.soundEnabled) {
             return
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.beep1)
        mediaPlayer?.start()
    }

    fun playTimerTick2() {
        if(!activityRunning || !globalSettings.soundEnabled) {
            return
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.beep2)
        mediaPlayer?.start()
    }

    fun playSuccessSound() {
        if(!activityRunning || !globalSettings.soundEnabled) {
            return
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.finish_success)
        mediaPlayer?.start()
    }
}